package me.ci.project.mio.api.actions;

import me.ci.project.mio.api.IMinecraftAgent;

/**
 * Contains an action that the agent should perform.
 */
public interface IAction
{
	/**
	 * Causes this action to cancel it's current execution.
	 *
	 * @throws IllegalStateException
	 *             If this action is not running or if it is not cancelable.
	 */
	void cancel();

	/**
	 * Gets the agent this action is controlling.
	 *
	 * @return The agent this action is attached to.
	 */
	IMinecraftAgent getAgent();

	/**
	 * Gets whether or not this action can be canceled.
	 *
	 * @return True if this action can be canceled, false otherwise.
	 */
	boolean isCancelable();

	/**
	 * Gets whether or not this action is currently being executed. A action is only
	 * considered to be running if it has been started and has not yet finished.
	 *
	 * @return True if this action is running, false otherwise.
	 */
	boolean isRunning();

	/**
	 * Causes this action to start being executed. If this action is already
	 * running, this method does nothing.
	 */
	void start();
}
