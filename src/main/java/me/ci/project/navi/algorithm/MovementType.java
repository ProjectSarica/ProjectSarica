package me.ci.project.navi.algorithm;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * A registered and executable way of moving between two path nodes.
 *
 * @author thedudefromci
 */
public class MovementType
{
	/**
	 * A builder utility that can be used to create a new movement type from a
	 * static field for easier registry on load.
	 *
	 * @author thedudefromci
	 *
	 * @param <T>
	 *            - The type of movement that will be instantiated.
	 */
	public static class Builder
	{
		/**
		 * Creates a new movement type builder for the given movement factory.
		 *
		 * @param <T>
		 *            - The type of movement that will be instantiated.
		 * @param factory
		 *            - The factory object that creates new movement instances.
		 *
		 * @return A new movement type builder for the provided factory.
		 */
		public static Builder of(@Nonnull IMovementFactory factory)
		{
			return new Builder(factory);
		}


		private final IMovementFactory factory;
		private String registry;


		private Builder(IMovementFactory factory)
		{
			this.factory = factory;
		}


		/**
		 * Creates a new movement type from the supplied properties.
		 *
		 * @return The new movement object.
		 *
		 * @throws IncompleteMovementTypeBuilderException
		 *             If this builder is missing required initiation properties for the
		 *             movement type.
		 */
		public MovementType build()
		{
			if (this.registry == null) throw new IncompleteMovementTypeBuilderException("Registry Name");

			return new MovementType(this.registry, this.factory);
		}


		/**
		 * Sets the registry that the movement type that will be used for identifying
		 * this movement type. This is a required initiation property.
		 *
		 * @param namespace
		 *            - The namespace to apply to the movement type.
		 * @param name
		 *            - The name of the movement type.
		 *
		 * @return This movement type builder for chaining purposes.
		 */
		public Builder setRegistryName(String namespace, String name)
		{
			this.registry = String.format("%s:%s", namespace, name);
			return this;
		}
	}


	/**
	 * Can be used to calculate a list of available movements of a specific type
	 * that can be applied from a given path node to find all neighboring path
	 * nodes.
	 *
	 * @author thedudefromci
	 *
	 * @param <T>
	 *            The movement instance that is being used for these neighboring
	 *            path node tests.
	 */
	public interface IMovementFactory
	{
		/**
		 * Finds all available movements that of a given type that can be applied from
		 * the provided path node position.
		 *
		 * @param node
		 *            - The path node to test against.
		 * @param nodeContext
		 *            - The path node context to use when creating the destination node.
		 * @param movements
		 *            - The list to add new movements to.
		 */
		void getMovements(PathNode node, PathNodeContext nodeContext, List<IMovement> movements);
	}


	/**
	 * A runtime exception that is thrown if a movement type builder is missing any
	 * required initiation properties.
	 *
	 * @author thedudefromci
	 */
	public static class IncompleteMovementTypeBuilderException extends RuntimeException
	{
		private static final long serialVersionUID = -8460681999140364451L;


		private IncompleteMovementTypeBuilderException(String type)
		{
			super(String.format("Movement type is missing required propery: %s", type));
		}
	}


	private final IMovementFactory factory;
	private final String registry;


	protected MovementType(String registry, IMovementFactory factory)
	{
		this.registry = registry;
		this.factory = factory;
	}


	/**
	 * Gets a list of all available movements of this type that can be applied, to
	 * reach neighboring path nodes from the provided input node.
	 *
	 * @param node
	 *            - The input node.
	 * @param nodeContext
	 *            - The path node context to use when creating the destination node.
	 * @param movements
	 *            - The list to add new movements to.
	 */
	public void getNeighbors(PathNode node, PathNodeContext context, List<IMovement> movements)
	{
		this.factory.getMovements(node, context, movements);
	}


	/**
	 * Gets the full registry name of this movement type. (Format namespace:name)
	 *
	 * @return The registry name of this movement type.
	 */
	public String getRegistryName()
	{
		return this.registry;
	}
}
