package me.ci.project.navi.registry;

import me.ci.project.navi.algorithm.MovementNetwork;

public final class ModMovementNetworks
{
	public static final MovementNetwork BASIC_MOVEMENT = new MovementNetwork()
		.registerMovementType(ModMovements.WALK);


	private ModMovementNetworks()
	{}
}
