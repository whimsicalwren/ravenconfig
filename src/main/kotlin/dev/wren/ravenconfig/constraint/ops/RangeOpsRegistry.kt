package dev.wren.ravenconfig.constraint.ops

import dev.wren.ravenconfig.constraint.ops.RangeOps.BigDecimalOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.BigIntegerOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.ByteOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.DoubleOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.FloatOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.IntOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.LongOps
import dev.wren.ravenconfig.constraint.ops.RangeOps.ShortOps
import java.math.BigDecimal
import java.math.BigInteger

object RangeOpsRegistry {

    private val ops: Map<Class<*>, RangeOps<*>> = mapOf(
        Byte::class.java to ByteOps,
        Short::class.java to ShortOps,
        Int::class.java to IntOps,
        Long::class.java to LongOps,
        Double::class.java to DoubleOps,
        Float::class.java to FloatOps,
        BigInteger::class.java to BigIntegerOps,
        BigDecimal::class.java to BigDecimalOps
    )

    @Suppress("UNCHECKED_CAST")
    fun <T : Comparable<T>> get(tClass: Class<T>): RangeOps<T>? {
        return ops[tClass] as? RangeOps<T>
    }
}