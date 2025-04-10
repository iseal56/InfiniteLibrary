package dev.iseal.infinitelibrary.client.models.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class LibrarianEntityModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart root;
	private final ModelPart LLeg;
	private final ModelPart RLeg;
	private final ModelPart Body;
	private final ModelPart XPOrb;
	private final ModelPart Head;
	private final ModelPart LArm;
	private final ModelPart RArm;

	public LibrarianEntityModel(ModelPart root) {
        super(root);
		this.root = root.getChild("root");
		this.LLeg = this.root.getChild("LLeg");
		this.RLeg = this.root.getChild("RLeg");
		this.Body = this.root.getChild("Body");
		this.XPOrb = this.Body.getChild("XPOrb");
		this.Head = this.root.getChild("Head");
		this.LArm = this.root.getChild("LArm");
		this.RArm = this.root.getChild("RArm");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData LLeg = root.addChild("LLeg", ModelPartBuilder.create().uv(-2, -2).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -26.0F, 5.0F, -0.4363F, 0.0F, 0.0F));

		ModelPartData cube_r1 = LLeg.addChild("cube_r1", ModelPartBuilder.create().uv(-2, -2).cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 13.0F, 1.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData RLeg = root.addChild("RLeg", ModelPartBuilder.create().uv(-2, -2).cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -25.0F, 5.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r2 = RLeg.addChild("cube_r2", ModelPartBuilder.create().uv(-2, -2).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 12.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData Body = root.addChild("Body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -50.0F, -4.0F));

		ModelPartData cube_r3 = Body.addChild("cube_r3", ModelPartBuilder.create().uv(-12, -2).cuboid(-11.0F, -13.0F, -3.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 12.0F, 5.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r4 = Body.addChild("cube_r4", ModelPartBuilder.create().uv(-12, -2).cuboid(-11.0F, -12.0F, -3.0F, 6.0F, 12.0F, 4.0F, new Dilation(0.0F))
				.uv(-6, -2).cuboid(-5.0F, -26.0F, -3.0F, 2.0F, 26.0F, 4.0F, new Dilation(0.0F))
				.uv(-6, -2).cuboid(-13.0F, -26.0F, -3.0F, 2.0F, 26.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 24.0F, 10.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData XPOrb = Body.addChild("XPOrb", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r5 = XPOrb.addChild("cube_r5", ModelPartBuilder.create().uv(-3, -2).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 5.0F, 1.122F, -0.003F, -0.3896F));

		ModelPartData Head = root.addChild("Head", ModelPartBuilder.create(), ModelTransform.of(0.0F, -50.0F, -3.0F, 0.48F, 0.0F, 0.0F));

		ModelPartData cube_r6 = Head.addChild("cube_r6", ModelPartBuilder.create().uv(-10, -6).cuboid(2.0F, -15.0F, -5.0F, 2.0F, 15.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, -5.0F, 0.2921F, -0.7203F, -0.1958F));

		ModelPartData cube_r7 = Head.addChild("cube_r7", ModelPartBuilder.create().uv(-11, -8).cuboid(3.0F, -16.0F, -6.0F, 1.0F, 16.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -5.0F, 0.2921F, -0.7203F, -0.1958F));

		ModelPartData cube_r8 = Head.addChild("cube_r8", ModelPartBuilder.create().uv(-9, -6).cuboid(-4.0F, -15.0F, -5.0F, 2.0F, 15.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, -5.0F, 0.257F, 0.5522F, 0.137F));

		ModelPartData cube_r9 = Head.addChild("cube_r9", ModelPartBuilder.create().uv(-11, -8).cuboid(-4.0F, -16.0F, -6.0F, 1.0F, 16.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -5.0F, 0.257F, 0.5522F, 0.137F));

		ModelPartData LArm = root.addChild("LArm", ModelPartBuilder.create(), ModelTransform.of(6.0F, -48.0F, -3.0F, -0.6545F, 0.0F, 0.0F));

		ModelPartData cube_r10 = LArm.addChild("cube_r10", ModelPartBuilder.create().uv(-2, -2).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 25.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0033F, 0.0552F, 0.1188F));

		ModelPartData RArm = root.addChild("RArm", ModelPartBuilder.create(), ModelTransform.of(-5.0F, -48.0F, -3.0F, -1.9199F, 0.0F, 0.0F));

		ModelPartData cube_r11 = RArm.addChild("cube_r11", ModelPartBuilder.create().uv(-2, -2).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 25.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, 1.2174F, 0.0924F, -0.0928F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(LivingEntityRenderState livingEntityRenderState) {
		super.setAngles(livingEntityRenderState);
	}
}