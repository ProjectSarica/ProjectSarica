package me.ci.project.sarica.registry;

import me.ci.project.sarica.ProjectSarica;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ModItems
{
    public static final Lazy<Item> NPC_SPAWN_EGG = spawnEgg("npc_spawn_egg", ModEntities.NPC);


    private static <T extends Entity> Lazy<Item> spawnEgg(String name, Lazy<EntityType<T>> entityType)
    {
        return Lazy.of(() ->
        {
            Item.Properties spawnEggProperties = new Item.Properties().tab(ItemGroup.TAB_MISC);
            SpawnEggItem spawnEgg = new SpawnEggItem(entityType.get(), 0xC5C5C5, 0x5EB1C8, spawnEggProperties);
            spawnEgg.setRegistryName(ProjectSarica.MOD_ID, name);
            return spawnEgg;
        });
    }


    @SubscribeEvent
    public static void register(Register<Item> event)
    {
        event.getRegistry().registerAll(NPC_SPAWN_EGG.get());
    }


    private ModItems()
    {}
}
