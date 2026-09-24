package dev.wren.ravenconfig.constraint.types

import dev.wren.ravenconfig.constraint.Constraint
import dev.wren.ravenconfig.entry.Scope

class PatternConstraint(
    regex: String,
    override val scope: Scope
) : Constraint {
    private val pattern = Regex(regex)

    override fun check(value: Any?): String? {
        val s = value as? CharSequence ?: return "expected a CharSequence value"
        return if (pattern.matches(s)) null else "must match pattern '${pattern.pattern}'"
    }

    override fun clamp(value: Any?): Any? = null // can't clamp

}