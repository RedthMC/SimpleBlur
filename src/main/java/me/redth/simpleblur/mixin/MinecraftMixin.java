package me.redth.simpleblur.mixin;

import me.redth.simpleblur.SimpleBlur;
import me.redth.simpleblur.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public GameSettings gameSettings;

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateDisplay()V"))
    private void setupBlur(CallbackInfo ci) {
        SimpleBlur.INSTANCE.blur();
    }

    @Inject(method = "getLimitFramerate", at = @At(value = "HEAD"), cancellable = true)
    public void unfocusedFPSLimiter(CallbackInfoReturnable<Integer> cir) {
        if (ModConfig.INSTANCE.enabled && ModConfig.INSTANCE.getApplyInMenus()) cir.setReturnValue(gameSettings.limitFramerate);
    }
}