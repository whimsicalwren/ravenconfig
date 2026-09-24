package dev.wren.ravenconfig.codec

interface ValueCodec<T> {
    fun decode(raw: Any?): T?
    fun encode(value: T): Any?
}