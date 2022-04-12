package me.ci.project.navi.registry;

import me.ci.project.navi.algorithm.MovementType;
import me.ci.project.navi.movements.WalkMovement;

public final class ModMovements
{
	public static final MovementType WALK = MovementType.Builder
		.of(WalkMovement::getMovements)
		.setRegistryName("projectnavi", "walk")
		.build();


	private ModMovements()
	{}
}
