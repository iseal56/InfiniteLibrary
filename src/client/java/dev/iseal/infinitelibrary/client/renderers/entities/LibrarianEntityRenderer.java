package dev.iseal.infinitelibrary.client.renderers.entities;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.entities.LibrarianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.util.Identifier;

public class LibrarianEntityRenderer extends MobEntityRenderer<LibrarianEntity, IllagerEntityRenderState, IllagerEntityModel<IllagerEntityRenderState>> {
    public LibrarianEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel<>(context.getPart(EntityModelLayers.ILLUSIONER)), 0.5f);
    }

    @Override
    public IllagerEntityRenderState createRenderState() {
        return new IllagerEntityRenderState();
    }

    @Override
    public Identifier getTexture(IllagerEntityRenderState state) {
        return IL.identifier("textures/entity/librarian.png");
    }
}
