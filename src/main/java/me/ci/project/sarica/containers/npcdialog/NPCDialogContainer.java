package me.ci.project.sarica.containers.npcdialog;

import me.ci.project.sarica.entities.npc.NPCEntity;
import me.ci.project.sarica.registry.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;

public class NPCDialogContainer extends Container
{
    public static IContainerProvider provider(NPCEntity npc)
    {
        return (containerId, playerInventory, player) -> new NPCDialogContainer(containerId, player, npc);
    }


    private final NPCEntity npc;
    private final PlayerEntity player;


    public NPCDialogContainer(int containerId, PlayerEntity player, NPCEntity npc)
    {
        super(ModContainers.NPC_DIALOG.get(), containerId);
        this.player = player;
        this.npc = npc;
    }


    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return this.player == player && player.distanceTo(this.npc) < 5;
    }

}
