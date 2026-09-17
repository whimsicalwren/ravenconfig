package dev.wren.ravenconfig.util

import com.electronwill.nightconfig.core.CommentedConfig
import dev.wren.ravenconfig.model.ConfigModelEntry
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
            pathAwareConfigValuesMap.getOrPut(configType) { HashMap() }[Util.sanitizeName(path.joinToString("."))] =
                Pair(entry, forgeValue)
        }

    /**
     *
     */
    abstract fun update(config: CommentedConfig)
}