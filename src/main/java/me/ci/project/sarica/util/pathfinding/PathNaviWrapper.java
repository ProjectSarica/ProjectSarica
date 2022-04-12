package me.ci.project.sarica.util.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

/**
 * This GroundPathNavigator wrapper is used to contain an entity's path
 * navigator into a form where all movement modifications can be observed and
 * path early cancellation or recomputation can be monitored and properly
 * handled.
 *
 * @author thedudefromci
 */
public class PathNaviWrapper extends GroundPathNavigator
{
	public PathNaviWrapper(MobEntity mob, World world)
	{
		super(mob, world);
	}

}
