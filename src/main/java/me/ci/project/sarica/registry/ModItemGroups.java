package me.ci.project.sarica.registry;

import me.ci.project.sarica.ProjectSarica;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.util.Lazy;

public final class ModItemGroups
{
    public static final Lazy<ItemGroup> DEBUG_ITEMS = group(Items.RED_WOOL, "debug");


    private static Lazy<ItemGroup> group(Item item, String name)
    {
        return Lazy.of(() -> new ItemGroup(ProjectSarica.MOD_ID + "." + name)
        {
            @Override
            public ItemStack makeIcon()
            {
                return new ItemStack(item);
            }
        });
    }


    private ModItemGroups()
    {}
}
