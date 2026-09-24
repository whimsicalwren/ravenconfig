package dev.wren.ravenconfig.constraint

import dev.wren.ravenconfig.entry.OnInvalid
import dev.wren.ravenconfig.entry.Scope

object ConstraintEvaluator {

    sealed class ValidationResult {
        data class Valid(val value: Any?) : ValidationResult()
        data class Corrected(val value: Any?, val reason: String) : ValidationResult()
        data class Failed(val reason: String) : ValidationResult()
    }

    fun evaluate(value: Any?, constraints: List<Constraint>, onInvalid: OnInvalid): ValidationResult {
        for (constraint in constraints) {
            if (constraint.scope == Scope.VALUE) {
                val error = constraint.check(value) ?: continue
                return resolve(value, constraint, error, onInvalid)
            } else {
                val collection = value as? Collection<*> ?: continue

                val badIndex = collection.indexOfFirst { constraint.check(it) != null }
                if (badIndex == -1) continue

                val error = constraint.check(collection.elementAt(badIndex))!!
                return resolveElements(value, collection, constraint, badIndex, error, onInvalid)
            }
        }
        return ValidationResult.Valid(value)
    }

    private fun resolve(value: Any?, constraint: Constraint, error: String, onInvalid: OnInvalid) =
        when (onInvalid) {
            OnInvalid.ERROR -> ValidationResult.Failed(error)
            OnInvalid.IGNORE -> ValidationResult.Valid(value)
            OnInvalid.RESET -> ValidationResult.Corrected(null, error) // use null + corrected as a reset
            OnInvalid.CLAMP -> {
                val clamped = constraint.clamp(value)
                if (clamped != null) ValidationResult.Corrected(clamped, error)
                else ValidationResult.Corrected(null, "$error (could not clamp, resetting)")
            }
            OnInvalid.INHERIT -> ValidationResult.Failed("INHERIT")
        }

    private fun resolveElements(value: Any?, collection: Collection<*>, constraint: Constraint, badIndex: Int, error: String, onInvalid: OnInvalid) = when (onInvalid) {
        OnInvalid.ERROR -> ValidationResult.Failed("element $badIndex: $error")
        OnInvalid.IGNORE -> ValidationResult.Valid(value)
        OnInvalid.RESET -> ValidationResult.Corrected(null, "element $badIndex: $error")
        OnInvalid.CLAMP -> {
            val fixed = collection.map { element ->
                if (constraint.check(element) != null) constraint.clamp(element) ?: element else element
            }
            ValidationResult.Corrected(fixed, "element $badIndex: $error")
        }
        OnInvalid.INHERIT -> ValidationResult.Failed("INHERIT")
    }


}