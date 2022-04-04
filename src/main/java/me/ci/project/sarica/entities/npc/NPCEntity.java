package me.ci.project.sarica.entities.npc;

import me.ci.project.sarica.mio.agent.MioAgent;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class NPCEntity extends CreatureEntity
{
	private final MioAgent agent = new MioAgent(this);


	public NPCEntity(EntityType<NPCEntity> entityType, World world)
	{
		super(entityType, world);

		setCustomNameVisible(true);
		setPersistenceRequired();
	}


	public MioAgent getAgent()
	{
		return this.agent;
	}
}
