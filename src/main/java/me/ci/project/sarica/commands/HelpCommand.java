package me.ci.project.sarica.commands;

import java.util.ArrayList;
import java.util.List;
import com.mojang.brigadier.context.CommandContext;
import me.ci.project.sarica.util.cmd.CommandExecutor;
import me.ci.project.sarica.util.cmd.IHelpSubcommand;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class HelpCommand implements IHelpSubcommand
{
	public static final String NAME = "help";
	public static final String DESCRIPTION = "Shows the description and usage of all ProjectSarica debug commands.";
	public static final String[] ARGUMENTS = {};

	private final List<CommandExecutor> commandList = new ArrayList<>();


	@Override
	public void addCommandToList(CommandExecutor command)
	{
		this.commandList.add(command);
	}


	@Override
	public boolean execute(CommandContext<CommandSource> context)
	{
		IFormattableTextComponent text = new StringTextComponent("Usage:").withStyle(TextFormatting.GRAY);

		for (CommandExecutor cmd : this.commandList)
		{
			text.append(new StringTextComponent("\n/projectsarica ").withStyle(TextFormatting.GOLD));
			text.append(new StringTextComponent(cmd.getCommandName()).withStyle(TextFormatting.DARK_AQUA));

			for (String arg : cmd.getArguments())
			{
				String argName = String.format(" <%s>", arg);
				text.append(new StringTextComponent(argName).withStyle(TextFormatting.DARK_AQUA));
			}

			text.append(new StringTextComponent(" - ").withStyle(TextFormatting.DARK_GRAY));
			text.append(new StringTextComponent(cmd.getCommandDescription()).withStyle(TextFormatting.GRAY));

		}

		context.getSource().sendSuccess(text, false);
		return true;
	}


	@Override
	public String[] getArguments()
	{
		return ARGUMENTS;
	}


	@Override
	public String getCommandDescription()
	{
		return DESCRIPTION;
	}


	@Override
	public String getCommandName()
	{
		return NAME;
	}
}
