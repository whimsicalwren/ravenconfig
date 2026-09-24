package dev.wren.ravenconfig.entry

@Target(AnnotationTarget.FIELD)
annotation class ConfigEntry(
    val name: String = "",
    val description: String = "",
    val onInvalid: OnInvalid = OnInvalid.INHERIT
)