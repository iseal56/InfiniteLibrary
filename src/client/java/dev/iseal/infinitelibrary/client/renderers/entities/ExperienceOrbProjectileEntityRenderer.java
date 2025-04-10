package dev.iseal.infinitelibrary.client.renderers.entities;

import dev.iseal.infinitelibrary.entities.projectiles.ExperienceOrbProjectileEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;

public class ExperienceOrbProjectileEntityRenderer extends EntityRenderer<ExperienceOrbProjectileEntity, ProjectileEntityRenderState> {
    public ExperienceOrbProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }
}
