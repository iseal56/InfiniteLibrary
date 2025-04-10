package dev.iseal.infinitelibrary.client.clientRegistries;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.client.models.entities.LibrarianEntityModel;
import dev.iseal.infinitelibrary.client.renderers.entities.ExperienceOrbProjectileEntityRenderer;
import dev.iseal.infinitelibrary.client.renderers.entities.LibrarianEntityRenderer;
import dev.iseal.infinitelibrary.client.renderers.items.SpellBookRenderer;
import dev.iseal.infinitelibrary.registry.EntityRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class RenderRegistry {

    private static RenderRegistry INSTANCE;
    public static RenderRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RenderRegistry();
        }
        return INSTANCE;
    }

    public void initialize() {
        EntityRendererRegistry.register(EntityRegistry.LIBRARIAN, LibrarianEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.EXPERIENCE_ORB_PROJECTILE, ExperienceOrbProjectileEntityRenderer::new);
    }


    private void registerSpellBook() {
        new SpellBookRenderer(SpellBookRenderer.getTexturedModelData().createModel());
    }
}
