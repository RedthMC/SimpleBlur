package me.redth.simpleblur

import me.redth.simpleblur.config.ModConfig
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.lwjgl.opengl.GL11

@Mod(modid = SimpleBlur.MODID, name = SimpleBlur.NAME, version = SimpleBlur.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object SimpleBlur {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"
    private val mc = Minecraft.getMinecraft()
    private var lastDrawn = 0L

    @Mod.EventHandler
    fun onInit(e: FMLInitializationEvent) {
        ModConfig.initialize()
    }

    fun blur() {
        if (!ModConfig.enabled) return
        if (!ModConfig.applyInMenus && mc.currentScreen != null) return
        if (ModConfig.multiplier == 0) return

        val now = System.nanoTime()
        val timeTaken = now - lastDrawn
        lastDrawn = now
        val accumValue = timeTaken.toDouble() * 0.0000033 / ModConfig.multiplier.toDouble() // dont ask me how i got the number
        val accumFloat = accumValue.toFloat().coerceIn(0f, 1f)

        GL11.glAccum(GL11.GL_MULT, 1f - accumFloat)
        GL11.glAccum(GL11.GL_ACCUM, accumFloat)
        GL11.glAccum(GL11.GL_RETURN, 1f)
    }
}