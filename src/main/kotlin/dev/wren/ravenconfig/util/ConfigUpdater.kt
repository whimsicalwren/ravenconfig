package dev.wren.ravenconfig.util

import com.electronwill.nightconfig.core.CommentedConfig
import dev.wren.ravenconfig.model.ConfigModel
import dev.wren.ravenconfig.model.category.ConfigModelCategory
import dev.wren.ravenconfig.model.entry.ConfigModelEntry
import net.neoforged.neoforge.common.ModConfigSpec
import java.util.EnumMap

/**
 * Utility class for updating configs.
 * TODO add example usage
 */
abstract class ConfigUpdater {

    val configValuesMap = HashMap<String, ModConfigSpec.ConfigValue<*>>()

    val configValueConsumer = { name: String, value: ModConfigSpec.ConfigValue<*> ->
        configValuesMap[name] = value
    }

    val pathAwareConfigValuesMap: EnumMap<ConfigType, HashMap<String, Pair<ConfigModelEntry<*>, ModConfigSpec.ConfigValue<*>>>> =
        EnumMap(ConfigType::class.java)

    fun pathAwareConsumerFor(configType: ConfigType) =
        { path: List<String>, entry: ConfigModelEntry<*>, forgeValue: ModConfigSpec.ConfigValue<*> ->
            val path = if (path.size == 1) {
                listOf("general") + path
            } else {
                path
            }
            pathAwareConfigValuesMap.getOrPut(configType) { HashMap() }[sanitizeName(path.joinToString("."))] =
                Pair(entry, forgeValue)
        }

    /**
     *
     */
    abstract fun update(config: CommentedConfig)

    companion object {
        /**
         * @param configObject fields belonging to this object that are annotated with [ConfigEntry] or [ConfigCategory] will be added to the config.
         */
        @JvmStatic
        fun buildConfigModel(configObject: Any) =
            ConfigModel.build(configObject)

        @JvmStatic
        fun buildConfigSpec(
            configCategory: ConfigModelCategory, builder: ModConfigSpec.Builder, path: List<String> = emptyList(),
            forgeConfigValueConsumer: (String, ModConfigSpec.ConfigValue<*>) -> Unit = { _, _ -> },
            pathAwareConsumer: (List<String>, ConfigModelEntry<*>, ModConfigSpec.ConfigValue<*>) -> Unit? = { _, _, _ -> }
        ): ModConfigSpec.Builder {
            for ((_, node) in configCategory.children) {
                if (node is ConfigModelCategory) {
                    builder.push(node.title)
                    buildConfigSpec(node, builder, path + node.title, forgeConfigValueConsumer, pathAwareConsumer)
                    builder.pop()

                } else if (node is ConfigModelEntry<*>) {
                    val value = defineNode(builder, node)
                    forgeConfigValueConsumer.invoke(node.name, value)
                    pathAwareConsumer.invoke(path + node.name, node, value)
                }
            }

            return builder
        }

        @JvmStatic
        fun <T> define(builder: ModConfigSpec.Builder, entry: ConfigModelEntry<*>, v: T): ModConfigSpec.ConfigValue<T> =
            builder.define(entry.name, v)

        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        fun <T : Enum<T>> defineEnum(builder: ModConfigSpec.Builder, entry: ConfigModelEntry<*>, value: Enum<*>): ModConfigSpec.EnumValue<*> =
            builder.defineEnum(entry.name, value as T)

        @JvmStatic
        fun <T : Comparable<T>> defineNumeric(builder: ModConfigSpec.Builder, entry: ConfigModelEntry<*>, v: T): ModConfigSpec.ConfigValue<out Comparable<*>> =
            if (entry.min == null || entry.max == null) {
                define(builder, entry, if (v is Float) v.toDouble() else v)
            } else {
                when (v) {
                    is Int -> builder.defineInRange(entry.name, v, entry.min as Int, entry.max as Int)
                    is Long -> builder.defineInRange(entry.name, v, entry.min as Long, entry.max as Long)
                    is Float -> builder.defineInRange(
                        entry.name,
                        v.toDouble(),
                        (entry.min as Float).toDouble(),
                        (entry.max as Float).toDouble()
                    )

                    is Double -> builder.defineInRange(entry.name, v, entry.min as Double, entry.max as Double)
                    else -> throw IllegalArgumentException("Non numeric type $v not accepted")
                }
            }

        @JvmStatic
        fun defineNode(builder: ModConfigSpec.Builder, entry: ConfigModelEntry<*>): ModConfigSpec.ConfigValue<*> {
            entry.description?.let(builder::comment)

            return when (val v = entry.getValue()) {
                is Int -> defineNumeric(builder, entry, v)
                is Long -> defineNumeric(builder, entry, v)
                is Float -> defineNumeric(builder, entry, v)
                is Double -> defineNumeric(builder, entry, v)
                is Boolean -> define(builder, entry, v)
                is String -> define(builder, entry, v)
                is Enum<*> -> defineEnum(builder, entry, v)
                else -> {
                    throw IllegalArgumentException("invalid config type $v of class ${v?.javaClass}")
                }
            }
        }

        @JvmStatic
        fun sanitizeName(category: String): String =
            category.replace(" ", "").filter { it.isLetterOrDigit() || it == '.' }
    }
}