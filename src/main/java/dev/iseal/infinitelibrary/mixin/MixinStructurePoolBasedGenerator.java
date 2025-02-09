package dev.iseal.infinitelibrary.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.BlockRotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockRotation.class)
public class MixinStructurePoolBasedGenerator {

    // change return value for my structures
    // maybe cause im dum
    @ModifyReturnValue(method = "random", at = @At("RETURN"))
    private static BlockRotation changeRotationForMyStructures(BlockRotation original) {
        boolean calledFromMyClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames.anyMatch(frame -> frame.getDeclaringClass().getName().contains("dev.iseal.infinitelibrary.worldgen.structures")));

        if (calledFromMyClass) {
            return BlockRotation.COUNTERCLOCKWISE_90;
        }
        return original;
    }
}