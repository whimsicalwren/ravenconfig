package dev.wren.ravenconfig.entry.constraints

import dev.wren.ravenconfig.entry.Scope

@Target(AnnotationTarget.PROPERTY)
annotation class Length(
    val max: Int = Int.MAX_VALUE,
    val scope: Scope = Scope.VALUE
)
