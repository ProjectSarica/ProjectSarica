package me.ci.project.navi.entities;

import me.ci.project.navi.algorithm.MovementNetwork;
import me.ci.project.navi.algorithm.Path;
import me.ci.project.navi.algorithm.PathExecutor;
import me.ci.project.navi.algorithm.PathFinder;
import me.ci.project.navi.goals.IGoal;
import me.ci.project.navi.goals.MoveToPositionGoal;
import me.ci.project.navi.registry.ModMovementNetworks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class DebugZombie extends MobEntity
{
	private PathExecutor pathExecutor;


	public DebugZombie(EntityType<DebugZombie> properties, World world)
	{
		super(properties, world);

		setInvulnerable(true);
		setCustomName(new StringTextComponent("Pathfinder"));
		setCustomNameVisible(true);
		setPersistenceRequired();

		this.pathExecutor = new PathExecutor(this);
	}


	@Override
	public void tick()
	{
		super.tick();
		if (this.level.isClientSide) return;

		this.pathExecutor.tick();

		if (!this.pathExecutor.isNavigating())
		{
			PlayerEntity player = this.level.getNearestPlayer(this, 100);

			if (player != null
				&& player.distanceTo(this) > 25)
			{
				IGoal goal = new MoveToPositionGoal(new BlockPos(player.position()));
				MovementNetwork movements = ModMovementNetworks.BASIC_MOVEMENT;
				BlockPos pos = new BlockPos(position());
				Path path = PathFinder.findPath(movements, this.level, this, goal, pos);
				this.pathExecutor.setPath(path);
			}

		}

	}
}
