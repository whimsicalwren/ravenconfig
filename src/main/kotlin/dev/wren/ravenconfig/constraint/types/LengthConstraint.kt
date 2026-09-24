package dev.wren.ravenconfig.constraint.types

import dev.wren.ravenconfig.constraint.Constraint
import dev.wren.ravenconfig.entry.Scope


class LengthConstraint(
    private val maxLength: Int,
    override val scope: Scope,
) : Constraint {

    // todo there has GOT to be a better way to do this :sob:
    private fun lengthOf(value: Any?) = when (value) {
        is CharSequence -> value.length
        is Collection<*> -> value.size
        is Map<*, *> -> value.size
        is Array<*> -> value.size
        is ByteArray -> value.size // i hate this
        is CharArray -> value.size
        is ShortArray -> value.size
        is IntArray -> value.size
        is LongArray -> value.size
        is FloatArray -> value.size
        is DoubleArray -> value.size
        is BooleanArray -> value.size
        else -> null
    }

    override fun check(value: Any?): String? {
        val len = lengthOf(value) ?: return "does not support length constraints"
        return if (len > maxLength) "length must be less than $maxLength (is $len)" else null
    }

    override fun clamp(value: Any?) = when (value) { // we can't really extend stuff to meet a min range so
        is CharSequence -> value.take(maxLength)
        is Collection<*> -> value.take(maxLength)
        is Map<*, *> -> value.take(maxLength)
        is Array<*> -> value.take(maxLength)
        is ByteArray -> value.take(maxLength) // this too eugh
        is CharArray -> value.take(maxLength)
        is ShortArray -> value.take(maxLength)
        is IntArray -> value.take(maxLength)
        is LongArray -> value.take(maxLength)
        is FloatArray -> value.take(maxLength)
        is DoubleArray -> value.take(maxLength)
        is BooleanArray -> value.take(maxLength)
        else -> null
    }


    fun <K, V> Map<K, V>.take(n: Int): Map<K, V> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        if (n == 0) return emptyMap()
        if (n >= this.size) return this
        var count = 0
        val map = mutableMapOf<K, V>()
        for ((key, value) in this) {
            map[key] = value
            if (++count == n)
                break
        }
        return map.toMap()
    }
}
