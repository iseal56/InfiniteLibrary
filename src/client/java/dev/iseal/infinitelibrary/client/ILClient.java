package dev.iseal.infinitelibrary.client;

import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Identifier;

public class ILClient implements ClientModInitializer {
    public static final EntityModelLayer BOOK_LAYER = new EntityModelLayer(Identifier.of("il", "spell_book"), "main");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(BOOK_LAYER, BookModelRenderer::getTexturedModelData);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.SPELL_BOOK, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            ModelPart root = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(BOOK_LAYER);
            new BookModelRenderer(root).render(stack, mode, matrices, vertexConsumers, light, overlay);
        });
    }
}
