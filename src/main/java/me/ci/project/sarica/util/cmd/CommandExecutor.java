package me.ci.project.sarica.util.cmd;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;

public interface CommandExecutor
{
	/**
	 * Executes this command.
	 *
	 * @return True if the command executed successfully, false otherwise.
	 *
	 * @throws CommandSyntaxException
	 */
	boolean execute(CommandContext<CommandSource> context) throws CommandSyntaxException;

	/**
	 * Gets an array of argument names that need to be passed to this command, in
	 * order, for the command to be executed.
	 *
	 * @return An array of command argument names.
	 */
	String[] getArguments();

	/**
	 * Gets the description text for this command.
	 *
	 * @return The command description.
	 */
	String getCommandDescription();

	/**
	 * Gets the name of this command.
	 *
	 * @return The command's name.
	 */
	String getCommandName();


	/**
	 * Gets whether or not this command requires the user to have increased
	 * permission levels. Defaults to true.
	 *
	 * @return True if the user must be a server operator to use this command. False
	 *         otherwise.
	 */
	default boolean requiresOp()
	{
		return true;
	}
}
