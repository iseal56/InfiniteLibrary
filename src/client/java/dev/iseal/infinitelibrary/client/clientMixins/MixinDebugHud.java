package dev.iseal.infinitelibrary.client.clientMixins;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class MixinDebugHud {

    @Shadow protected abstract void drawText(DrawContext context, List<String> text, boolean left);

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    public void render(DrawContext context, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world == null) return;
        if (MinecraftClient.getInstance().world.getRegistryKey() != IL.WORLD_KEY) return;
        drawText(context, List.of("No lmao"), true);
        ci.cancel();
    }
}
