package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
/**
 * Requires values to match a set regular expression. Only valid for types extending [CharSequence], like strings.
 *
 * @param regex The regular expression to use.
 * @param scope The scope of this annotation.
 */
annotation class Pattern (
    val regex: String,
    val scope: Scope = Scope.VALUE
)
