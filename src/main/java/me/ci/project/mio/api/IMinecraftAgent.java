package me.ci.project.mio.api;

import org.joml.Vector3f;
import me.ci.project.mio.api.actions.IAction;

/**
 * A bridge for interacting with the Minecraft agent entity within the game.
 *
 * @author thedudefromci
 */
public interface IMinecraftAgent
{
	/**
	 * Gets the action that this agent is currently performing.
	 *
	 * @return The agent's current action, or null if the agent is not doing
	 *         anything.
	 */
	IAction getAction();

	/**
	 * Gets the current position of the agent.
	 *
	 * @return The current world position.
	 */
	Vector3f getPosition();

	/**
	 * Assigns a new action to this agent and begins execution. If this agent is
	 * already in the middle of a running action, the current action is cancelled.
	 *
	 * @param action
	 *            - The action to execute.
	 *
	 * @throws IllegalStateException
	 *             - If the agent is currently performing and action that cannot be
	 *             cancelled.
	 */
	void setAction(IAction action);
}
