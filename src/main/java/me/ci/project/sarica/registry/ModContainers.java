package me.ci.project.sarica.registry;

import me.ci.project.sarica.ProjectSarica;
import me.ci.project.sarica.containers.npcdialog.NPCDialogContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ProjectSarica.MOD_ID, bus = Bus.MOD)
public final class ModContainers
{
    public static final Lazy<ContainerType<NPCDialogContainer>> NPC_DIALOG = dialog("npc_dialog", NPCDialogContainer::new);


    private static <T extends Container, E extends Entity> Lazy<ContainerType<T>> dialog(String name, IDialogContainerProvider<T, E> provider)
    {
        return container(name, (containerId, inventory) -> provider.create(containerId, inventory.player, null));
    }


    private static <T extends Container> Lazy<ContainerType<T>> container(String name, ContainerType.IFactory<T> factory)
    {
        return Lazy.of(() ->
        {
            ResourceLocation resource = new ResourceLocation(ProjectSarica.MOD_ID, name);
            ContainerType<T> containerType = new ContainerType<>(factory);
            containerType.setRegistryName(resource);
            return containerType;
        });
    }


    private interface IDialogContainerProvider<T extends Container, E extends Entity>
    {
        T create(int containerId, PlayerEntity player, E entity);
    }


    @SubscribeEvent
    public static void register(Register<ContainerType<?>> event)
    {
        event.getRegistry().registerAll(NPC_DIALOG.get());
    }


    private ModContainers()
    {}
}
