package me.ci.project.sarica.entities.npc;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class NpcRenderer extends MobRenderer<Npc, NpcModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft:textures/entity/steve.png");

    public NpcRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new NpcModel(false), 0.3f);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ArrowLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new BeeStingerLayer<>(this));
    }


    @Override
    public ResourceLocation getTextureLocation(Npc entity)
    {
        return TEXTURE;
    }


    @Override
    public Vector3d getRenderOffset(Npc pEntity, float pPartialTicks)
    {
        return pEntity
            .isCrouching() ? new Vector3d(0.0D, -0.125D, 0.0D) : super.getRenderOffset(pEntity, pPartialTicks);
    }


    private void setModelProperties(Npc pClientPlayer)
    {
        NpcModel playermodel = this.getModel();
        playermodel.crouching = pClientPlayer.isCrouching();
        BipedModel.ArmPose mainArmPose = getArmPose(pClientPlayer, Hand.MAIN_HAND);
        BipedModel.ArmPose offArmPose = getArmPose(pClientPlayer, Hand.OFF_HAND);

        if (mainArmPose.isTwoHanded())
        {
            offArmPose = pClientPlayer.getOffhandItem().isEmpty() ? BipedModel.ArmPose.EMPTY : BipedModel.ArmPose.ITEM;
        }

        if (pClientPlayer.getMainArm() == HandSide.RIGHT)
        {
            playermodel.rightArmPose = mainArmPose;
            playermodel.leftArmPose = offArmPose;
        }
        else
        {
            playermodel.rightArmPose = offArmPose;
            playermodel.leftArmPose = mainArmPose;
        }

    }


    @Override
    protected void scale(Npc pLivingEntity, MatrixStack pMatrixStack, float pPartialTickTime)
    {
        pMatrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }


    public void renderRightHand(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, Npc pPlayer)
    {
        this
            .renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, (this.model).rightArm, (this.model).rightSleeve);
    }


    public void renderLeftHand(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, Npc pPlayer)
    {
        this.renderHand(pMatrixStack, pBuffer, pCombinedLight, pPlayer, (this.model).leftArm, (this.model).leftSleeve);
    }


    private void renderHand(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, Npc pPlayer, ModelRenderer pRendererArm, ModelRenderer pRendererArmwear)
    {
        NpcModel playermodel = this.getModel();
        this.setModelProperties(pPlayer);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        pRendererArm.xRot = 0.0F;
        pRendererArm
            .render(pMatrixStack, pBuffer
                .getBuffer(RenderType.entitySolid(TEXTURE)), pCombinedLight, OverlayTexture.NO_OVERLAY);
        pRendererArmwear.xRot = 0.0F;
        pRendererArmwear
            .render(pMatrixStack, pBuffer
                .getBuffer(RenderType.entityTranslucent(TEXTURE)), pCombinedLight, OverlayTexture.NO_OVERLAY);
    }


    @Override
    protected void setupRotations(Npc entity, MatrixStack matrix, float age, float yaw, float partialTicks)
    {
        float f = entity.getSwimAmount(partialTicks);

        if (entity.isFallFlying())
        {
            super.setupRotations(entity, matrix, age, yaw, partialTicks);
            float f1 = entity.getFallFlyingTicks() + partialTicks;
            float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);

            if (!entity.isAutoSpinAttack())
            {
                matrix.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entity.xRot)));
            }

            Vector3d vector3d = entity.getViewVector(partialTicks);
            Vector3d vector3d1 = entity.getDeltaMovement();
            double d0 = Entity.getHorizontalDistanceSqr(vector3d1);
            double d1 = Entity.getHorizontalDistanceSqr(vector3d);

            if (d0 > 0.0D && d1 > 0.0D)
            {
                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                matrix.mulPose(Vector3f.YP.rotation((float) (Math.signum(d3) * Math.acos(d2))));
            }

        }
        else if (f > 0.0F)
        {
            super.setupRotations(entity, matrix, age, yaw, partialTicks);
            float f3 = entity.isInWater() ? -90.0F - entity.xRot : -90.0F;
            float f4 = MathHelper.lerp(f, 0.0F, f3);
            matrix.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (entity.isVisuallySwimming()) matrix.translate(0.0D, -1.0D, 0.3D);
        }
        else super.setupRotations(entity, matrix, age, yaw, partialTicks);

    }


    private static BipedModel.ArmPose getArmPose(Npc entity, Hand hand)
    {
        ItemStack itemstack = entity.getItemInHand(hand);
        if (itemstack.isEmpty()) return BipedModel.ArmPose.EMPTY;

        if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0)
        {
            UseAction useaction = itemstack.getUseAnimation();
            if (useaction == UseAction.BLOCK) return BipedModel.ArmPose.BLOCK;
            if (useaction == UseAction.BOW) return BipedModel.ArmPose.BOW_AND_ARROW;
            if (useaction == UseAction.SPEAR) return BipedModel.ArmPose.THROW_SPEAR;
            if (useaction == UseAction.CROSSBOW && hand == entity
                .getUsedItemHand()) return BipedModel.ArmPose.CROSSBOW_CHARGE;
        }
        else if (!entity.swinging && itemstack.getItem() == Items.CROSSBOW && CrossbowItem
            .isCharged(itemstack)) return BipedModel.ArmPose.CROSSBOW_HOLD;

        return BipedModel.ArmPose.ITEM;
    }

}
