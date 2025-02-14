package dev.iseal.infinitelibrary.client.renderers;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.EntityRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class RenderRegistry {

    private static RenderRegistry INSTANCE;
    public static RenderRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RenderRegistry();
        }
        return INSTANCE;
    }

    public static final EntityModelLayer BOOK_LAYER = new EntityModelLayer(new Identifier(IL.MOD_ID, "spell_book"), "main");

    public void initialize() {
        registerSpellBook();
    }

    private void registerSpellBook() {
        EntityModelLayerRegistry.registerModelLayer(BOOK_LAYER, dev.iseal.infinitelibrary.client.BookModelRenderer::getTexturedModelData);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.SPELL_BOOK, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            ModelPart root = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(BOOK_LAYER);
            new dev.iseal.infinitelibrary.client.BookModelRenderer(root).render(stack, mode, matrices, vertexConsumers, light, overlay);
        });
    }
}
