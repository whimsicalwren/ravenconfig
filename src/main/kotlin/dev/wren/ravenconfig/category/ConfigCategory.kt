package dev.wren.ravenconfig.category

import dev.wren.ravenconfig.entry.OnInvalid

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class ConfigCategory(
    val name: String = "",
    val description: String = "",
    val onInvalid: OnInvalid = OnInvalid.RESET
)