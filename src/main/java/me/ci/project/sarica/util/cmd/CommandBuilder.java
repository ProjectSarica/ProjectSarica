package me.ci.project.sarica.util.cmd;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ci.project.sarica.ProjectSarica;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandBuilder
{
	private interface CommandExecutorResponse
	{
		boolean execute(CommandContext<CommandSource> context) throws CommandSyntaxException;
	}


	public class SubcommandBuilder
	{
		private final ArgumentBuilder<CommandSource, ?> subcmd;
		private final SubcommandBuilder parent;
		private final CommandExecutor executor;


		private SubcommandBuilder(CommandExecutor executor)
		{
			this.parent = null;
			this.executor = executor;
			this.subcmd = Commands.literal(executor.getCommandName());

			CommandBuilder.this.help.addCommandToList(executor);
		}


		private SubcommandBuilder(SubcommandBuilder parent, CommandExecutor executor, String name, ArgumentType<?> argType)
		{
			this.parent = parent;
			this.executor = executor;
			this.subcmd = Commands.argument(name, argType);
		}


		public <T> SubcommandBuilder arg(String name, ArgumentType<T> argType)
		{
			return new SubcommandBuilder(this, this.executor, name, argType);
		}


		public CommandBuilder buildSubcommand()
		{
			this.subcmd.executes(errorHandler(this.executor::execute));
			if (this.executor.requiresOp()) this.subcmd.requires(s -> s.hasPermission(1));

			if (this.parent == null)
			{
				CommandBuilder.this.cmd.then(this.subcmd);
				return CommandBuilder.this;
			}
			else
			{
				this.parent.subcmd.then(this.subcmd);
				return this.parent.buildSubcommand();
			}

		}
	}


	private static Command<CommandSource> errorHandler(CommandExecutorResponse command)
	{
		return context ->
		{

			try
			{
				return command.execute(context) ? 1 : 0;
			}
			catch (CommandSyntaxException e)
			{
				ITextComponent message = new StringTextComponent(e.getMessage())
					.withStyle(TextFormatting.RED);

				context.getSource().sendFailure(message);
				return 0;
			}
			catch (Exception e)
			{
				ITextComponent message = new StringTextComponent("An internal error has occured while executing this command! Please see console for more information.")
					.withStyle(TextFormatting.RED);

				context.getSource().sendFailure(message);
				ProjectSarica.LOGGER.error("An internal error has occured while executing command!", e);
				return 0;
			}

		};
	}


	private final LiteralArgumentBuilder<CommandSource> cmd;
	private final IHelpSubcommand help;


	public CommandBuilder(String namespace, IHelpSubcommand help)
	{
		this.cmd = Commands.literal(namespace);
		this.cmd.executes(errorHandler(help::execute));
		this.help = help;
	}


	public SubcommandBuilder addSubcommand(CommandExecutor subcommand)
	{
		return new SubcommandBuilder(subcommand);
	}


	public LiteralArgumentBuilder<CommandSource> buildNamespace()
	{
		return this.cmd;
	}

}
