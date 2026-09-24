package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
/**
 * Limits values to a maximum length. Valid for [Collection], [Array], [Map], [CharSequence], and a variety of other array types.
 *
 * @param max The maximum length for the value.
 * @param scope The scope of this annotation.
 */
annotation class Length (
    val max: Int = Int.MAX_VALUE,
    val scope: Scope = Scope.VALUE
)
