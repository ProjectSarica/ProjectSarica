package me.ci.project.sarica.commands.base;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;

public interface CommandExecutor
{
    /**
     * Gets the name of this command.
     *
     * @return The command's name.
     */
    String getCommandName();

    /**
     * Gets an array of arguments that need to be passed to this command, in order,
     * for the command to be executed.
     *
     * @return An array of command arguments.
     */
    CommandArgument<?>[] getArguments();

    /**
     * Gets the description text for this command.
     *
     * @reutrn The command description.
     */
    String getCommandDescription();

    /**
     * Executes this command.
     *
     * @return True if the command executed successfully, false otherwise.
     */
    boolean execute(CommandContext<CommandSource> context);


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
