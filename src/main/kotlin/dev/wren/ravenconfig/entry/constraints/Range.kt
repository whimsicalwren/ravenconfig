package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
/**
 * Limits the minimum and maximum values of a config entry (must be a numeric value).
 * [min] and [max] are strings as that allows for parsing values depending on the type of the field.
 * If the value is [INFINITY] / [NEGATIVE_INFINITY], it is parsed into positive and negative infinity for doubles and floats, or
 * min and max for other values.
 * If the value is [MIN] / [MAX], it is parsed into the minimum or maximum values for that type. For doubles and floats, [MIN] is
 * parsed into the negative version of their max value, as min value for floats and doubles represents the smallest positive value.
 * If the value is not one of these, it is parsed by String.to____OrNull() from StringNumberConversionsJVM.kt.
 *
 * @param min The minimum value for this range.
 * @param max The maximum value for this range.
 * @param scope The scope of value this range applies to.
 */
annotation class Range (
    val min: String = "-inf",
    val max: String = "inf",
    val scope: Scope = Scope.VALUE
) {
    companion object {
        const val INFINITY = "inf"
        const val NEGATIVE_INFINITY = "-inf"
        const val MAX = "max"
        const val MIN = "min"
        fun of(number: Number) = number.toString()
    }
}
