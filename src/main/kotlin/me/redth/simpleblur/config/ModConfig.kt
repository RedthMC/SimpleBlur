package me.redth.simpleblur.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import me.redth.simpleblur.SimpleBlur

object ModConfig : Config(Mod(SimpleBlur.NAME, ModType.UTIL_QOL), "${SimpleBlur.MODID}.json") {
    @Slider(name = "Multiplier", min = 0f, max = 100f)
    var multiplier = 40

    @Switch(name = "Apply In Menus")
    var applyInMenus = true

    init {
        initialize()
    }
}