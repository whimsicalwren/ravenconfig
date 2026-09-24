package dev.wren.ravenconfig.entry

@Target(AnnotationTarget.FIELD)
/**
 * Represents an entry in a config.
 *
 * @param name The name of this entry. Defaults to the name of the field this annotation is on if blank.
 * @param description A description for this entry.
 * @param onInvalid What should happen if the value for this entry is invalid. Defaults to [OnInvalid.INHERIT].
 */
annotation class ConfigEntry(
    val name: String = "",
    val description: String = "",
    val onInvalid: OnInvalid = OnInvalid.INHERIT
)