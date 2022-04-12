package me.ci.project.navi.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles the creation and networking of path nodes. This can be
 * used to describe how path nodes can be reached from other path nodes, and the
 * distance between them.
 *
 * @author thedudefromci
 */
public class MovementNetwork
{
	private final Map<String, MovementType> movementTypes = new HashMap<>();


	/**
	 * Gets a list of all movements that can be applied from the given input node.
	 *
	 * @param node
	 *            - The input node.
	 * @param nodeContext
	 *            - The context to use for creating new path nodes.
	 * @param movements
	 *            - The list to add the movements to.
	 */
	public void getAvailableMovements(PathNode node, PathNodeContext nodeContext, List<IMovement> movements)
	{
		for (MovementType type : this.movementTypes.values())
			type.getNeighbors(node, nodeContext, movements);
	}


	/**
	 * Registers a new movement type to this network. This method does nothing if
	 * the movement type is null. If there is already an existing movement type with
	 * the registered name, it is replaced. <br/>
	 * <br/>
	 * Note that this method should only be called during the setup and registry
	 * events provided by Forge to prevent memory access errors and bugs during
	 * pathfinding or concurrent modification exceptions.
	 *
	 * @param movementType
	 *            - The movement type to add.
	 *
	 * @return This movement network for chaining purposes.
	 */
	public MovementNetwork registerMovementType(MovementType movementType)
	{
		if (movementType == null) return this;
		this.movementTypes.put(movementType.getRegistryName(), movementType);
		return this;
	}
}
