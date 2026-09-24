package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
annotation class Pattern(
    val regex: String,
    val scope: Scope = Scope.VALUE
)
