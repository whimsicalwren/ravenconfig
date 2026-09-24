package dev.wren.ravenconfig.entry

enum class Scope {
    /**
     * This constraint applies directly to the value it is placed on.
     */
    VALUE,
    /**
     * This constraint applies to all elements of the value it is placed on, if the value is a [Collection].
     * If the value is not a collection, this constraint is ignored.
     */
    ELEMENTS
}