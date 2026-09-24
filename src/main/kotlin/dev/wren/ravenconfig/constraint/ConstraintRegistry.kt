package dev.wren.ravenconfig.constraint

import dev.wren.ravenconfig.constraint.ops.RangeOpsRegistry
import dev.wren.ravenconfig.constraint.types.LengthConstraint
import dev.wren.ravenconfig.constraint.types.PatternConstraint
import dev.wren.ravenconfig.constraint.types.RangeConstraint
import dev.wren.ravenconfig.entry.constraints.Length
import dev.wren.ravenconfig.entry.constraints.Pattern
import dev.wren.ravenconfig.entry.constraints.Range
import java.util.function.BiFunction

object ConstraintRegistry {

    private val factories = mutableMapOf<Class<out Annotation>, BiFunction<Annotation, Class<*>, out Constraint>>()

    init {
        register(Range::class.java) { annotation, type ->
            annotation as Range
            val ops = RangeOpsRegistry.getOrThrow<Comparable<Any>>(type)

            RangeConstraint(ops, ops.parse(annotation.min), ops.parse(annotation.max), annotation.scope)
        }
        register(Length::class.java) { annotation, _ ->
            annotation as Length
            LengthConstraint(annotation.max, annotation.scope)
        }
        register(Pattern::class.java) { annotation, _ ->
            annotation as Pattern
            PatternConstraint(annotation.regex, annotation.scope)
        }
    }

    fun register(annotationClass: Class<out Annotation>, factory: BiFunction<Annotation, Class<*>, Constraint>) {
        factories[annotationClass] = factory
    }

    fun isRegistered(annotationClass: Class<out Annotation>): Boolean = factories.containsKey(annotationClass)

    fun build(annotation: Annotation, type: Class<*>): Constraint {
        val factory = factories[annotation::class.java] ?: error("No factory registered for ${annotation::class.simpleName}")
        return factory.apply(annotation, type)
    }
}
