package me.ci.project.mio.world;

import net.minecraft.util.math.shapes.VoxelShape;

/**
 * An immutable collection of properties that define a block's current state
 * within the world.
 *
 * @author thedudefromci
 */
public interface IBlockState
{
	/**
	 * Gets the collision shape of this block.
	 *
	 * @return The collision shape.
	 */
	VoxelShape getCollision();

	/**
	 * Gets the value of the property with the given name attached to this block
	 * state.
	 *
	 * @param property
	 *            - The property to get.
	 *
	 * @return The value of the property, or null if the property does not exist.
	 */
	String getProperty(String property);

	/**
	 * Gets the full registry name of this block state in the format:
	 *
	 * <pre>
	 * namespace:name[property=value,...]
	 * </pre>
	 *
	 * @return The registry name of this block state.
	 */
	String getRegistryName();

	/**
	 * Checks whether or not this block state defines the given property.
	 *
	 * @param property
	 *            - The property to look for.
	 *
	 * @return True if the property is defined, false otherwise.
	 */
	boolean hasProperty(String property);
}
