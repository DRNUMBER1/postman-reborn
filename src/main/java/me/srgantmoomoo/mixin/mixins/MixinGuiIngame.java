package me.srgantmoomoo.mixin.mixins;

import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postman.client.module.modules.render.NoRender;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
	protected void renderPotionEffectsHook(ScaledResolution scaledRes, CallbackInfo callbackInfo) {
		if (ModuleManager.isModuleEnabled("noRender") && ((NoRender)ModuleManager.getModuleByName("noRender")).potionIndicators.isEnabled()) {
			callbackInfo.cancel();
		}
	}
}