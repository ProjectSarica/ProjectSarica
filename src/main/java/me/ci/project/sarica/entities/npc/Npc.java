package me.ci.project.sarica.entities.npc;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Npc extends CreatureEntity
{
    public Npc(EntityType<Npc> entityType, World world)
    {
        super(entityType, world);

        setCustomNameVisible(true);
        setPersistenceRequired();
    }


    @Override
    protected boolean shouldDropExperience()
    {
        return false;
    }

}
