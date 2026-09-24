package dev.wren.ravenconfig.entry

enum class OnInvalid {
    /**
     * This entry should use the onInvalid of the category it belongs to.
     */
    INHERIT,

    /**
     * This entry should reset to its default value if invalid
     */
    RESET,

    /**
     * This entry should clamp its value to a set range or length
     *
     * @see dev.wren.ravenconfig.entry.constraints.Length
     * @see dev.wren.ravenconfig.entry.constraints.Range
     */
    CLAMP,

    /**
     * Invalid values should be ignored.
     */
    IGNORE,

    /**
     * Return an error if the value is invalid.
     */
    ERROR
}