package dev.wren.ravenconfig.entry

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
/**
 * Represents a config category.
 * @param name The name of this category. If left blank, this defaults to the name of the class or property it is placed on.
 * @param description The description of this category.
 * @param onInvalid What entries in this category with their onInvalid set to [OnInvalid.INHERIT], should do if invalid. Default value is [OnInvalid.RESET].
 */
annotation class ConfigCategory(
    val name: String = "",
    val description: String = "",
    val onInvalid: OnInvalid = OnInvalid.RESET
)