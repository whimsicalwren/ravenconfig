package dev.wren.ravenconfig.constraint.ops

import dev.wren.ravenconfig.entry.constraints.Range
import java.math.BigDecimal
import java.math.BigInteger

interface RangeOps<T : Comparable<T>> {
    fun parse(s: String): T?
    fun clamp(value: T, min: T?, max: T?): T = when {
        min != null && value < min -> min
        max != null && value > max -> max
        else -> value
    }

    object ByteOps : RangeOps<Byte> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN -> Byte.MIN_VALUE
            Range.INFINITY, Range.MAX -> Byte.MAX_VALUE
            else -> s.toByteOrNull()
        }
    }

    object ShortOps : RangeOps<Short> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN -> Short.MIN_VALUE
            Range.INFINITY, Range.MAX -> Short.MAX_VALUE
            else -> s.toShortOrNull()
        }
    }

    object IntOps : RangeOps<Int> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN -> Int.MIN_VALUE
            Range.INFINITY, Range.MAX -> Int.MAX_VALUE
            else -> s.toIntOrNull()
        }
    }

    object LongOps : RangeOps<Long> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN -> Long.MIN_VALUE
            Range.INFINITY, Range.MAX -> Long.MAX_VALUE
            else -> s.toLongOrNull()
        }
    }

    object DoubleOps : RangeOps<Double> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY -> Double.NEGATIVE_INFINITY
            Range.MIN -> -Double.MAX_VALUE
            Range.MAX -> Double.MAX_VALUE
            Range.INFINITY -> Double.POSITIVE_INFINITY
            else -> s.toDoubleOrNull()
        }
    }

    object FloatOps : RangeOps<Float> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY -> Float.NEGATIVE_INFINITY
            Range.MIN -> -Float.MAX_VALUE
            Range.MAX -> Float.MAX_VALUE
            Range.INFINITY -> Float.POSITIVE_INFINITY
            else -> s.toFloatOrNull()
        }
    }

    object BigIntegerOps : RangeOps<BigInteger> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN, Range.MAX, Range.INFINITY -> null
            else -> BigInteger(s)
        }
    }

    object BigDecimalOps : RangeOps<BigDecimal> {
        override fun parse(s: String) = when (s) {
            Range.NEGATIVE_INFINITY, Range.MIN, Range.MAX, Range.INFINITY -> null
            else -> BigDecimal(s)
        }
    }
}