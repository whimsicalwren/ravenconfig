package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
annotation class Range(
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
