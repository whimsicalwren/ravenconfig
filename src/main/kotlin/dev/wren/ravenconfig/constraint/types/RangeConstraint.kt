package dev.wren.ravenconfig.constraint.types

import dev.wren.ravenconfig.constraint.Constraint
import dev.wren.ravenconfig.constraint.ops.RangeOps
import dev.wren.ravenconfig.entry.Scope
import kotlin.ranges.rangeTo

class RangeConstraint<T : Comparable<T>>(
    private val ops: RangeOps<T>,
    private val min: T?,
    private val max: T?,
    override val scope: Scope
) : Constraint {

    @Suppress("UNCHECKED_CAST")
    override fun check(value: Any?): String? {
        val v = value as? T ?: return "expected a comparable numeric value"

        val belowMin = min != null && v < min
        val aboveMax = max != null && v > max

        if (!belowMin && !aboveMax) return null // in range

        val lower = min?.toString() ?: "-∞" // i dont like doing symbols but idk what else so
        val upper = max?.toString() ?: "∞"

        return "must be between $lower and $upper (was $v)"
    }

    @Suppress("UNCHECKED_CAST")
    override fun clamp(value: Any?): Any? {
        val v = value as? T ?: return null
        return ops.clamp(v, min, max)
    }

}