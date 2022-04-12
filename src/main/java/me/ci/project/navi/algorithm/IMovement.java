package me.ci.project.navi.algorithm;

import net.minecraft.entity.MobEntity;

/**
 * Represents a movement between two locations and world states.
 *
 * @author thedudefromci
 */
public interface IMovement
{
	/**
	 * Causes this movement instance to be applied to the target entity. This is
	 * called every tick until the movement is completed.
	 *
	 * @param entity
	 *            - The entity to move.
	 *
	 * @return True if this movement is finished, false otherwise.
	 */
	boolean executeTick(MobEntity entity);

	/**
	 * Gets the total cost of this movement. A cost of 1 indicates the agent will
	 * walk one meter. Higher values indicated a move difficult movement, longer
	 * time span, or a generally less favorable movement style.
	 *
	 * @return The cost.
	 */
	float getCost();

	/**
	 * Gets the path node that would be active after the pathfinding agent uses this
	 * movement.
	 *
	 * @return The theorectial end position and world state after this movement is
	 *         applied.
	 */
	PathNode getPosition();
}
