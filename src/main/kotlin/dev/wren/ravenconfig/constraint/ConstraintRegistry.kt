package dev.wren.ravenconfig.constraint

import dev.wren.ravenconfig.constraint.types.LengthConstraint
import dev.wren.ravenconfig.entry.constraints.Length
import java.util.function.Function

object ConstraintRegistry {
    val map = mutableMapOf<Class<out Annotation>, Function<out Annotation, Constraint>>().apply {
        put(Length::class.java) { annotation ->
            annotation as Length
            LengthConstraint(annotation.max, annotation.scope)
        }
    }

    fun get(annotationClass: Class<out Annotation>): Function<out Annotation, Constraint>? = map[annotationClass]

    fun <A : Annotation> register(annotationClass: Class<A>, factory: Function<A, Constraint>) {

    }
}