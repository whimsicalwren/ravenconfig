package dev.wren.ravenconfig.constraint

import dev.wren.ravenconfig.entry.Scope

interface Constraint {
    val scope: Scope
    fun check(value: Any?): String? // returns error message, or null if okay
    fun clamp(value: Any?): Any?
}