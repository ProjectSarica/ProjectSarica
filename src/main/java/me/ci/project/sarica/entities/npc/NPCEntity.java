package me.ci.project.sarica.entities.npc;

import me.ci.project.sarica.containers.npcdialog.NPCDialogContainer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;

public class NPCEntity extends CreatureEntity
{
    public NPCEntity(EntityType<NPCEntity> entityType, World world)
    {
        super(entityType, world);

        setCustomNameVisible(true);
        setPersistenceRequired();
    }


    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand)
    {
        if (!this.level.isClientSide) onRightClick(player);
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }


    protected void onRightClick(PlayerEntity player)
    {
        IContainerProvider containerProvider = NPCDialogContainer.provider(this);
        ITextComponent title = getDialogTitle();
        player.openMenu(new SimpleNamedContainerProvider(containerProvider, title));
    }


    protected ITextComponent getDialogTitle()
    {
        return new StringTextComponent("")
            .append(new StringTextComponent("NPC Dialog")
                .setStyle(Style.EMPTY.withColor(Color.parseColor("#bcbcbc"))))
            .append(getCustomName()
                .copy()
                .setStyle(Style.EMPTY.withColor(Color.parseColor("#68b2c6"))));
    }
}
