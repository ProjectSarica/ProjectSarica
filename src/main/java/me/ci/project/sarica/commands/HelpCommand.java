package me.ci.project.sarica.commands;

import java.util.ArrayList;
import java.util.List;
import com.mojang.brigadier.context.CommandContext;
import me.ci.project.sarica.commands.base.CommandArgument;
import me.ci.project.sarica.commands.base.CommandExecutor;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class HelpCommand implements CommandExecutor
{
    public static final String NAME = "help";
    public static final String DESCRIPTION = "Shows the description and usage of all ProjectSarica debug commands.";
    public static final CommandArgument<?>[] ARGUMENTS = {};

    private final List<CommandExecutor> commandList = new ArrayList<>();


    @Override
    public String getCommandName()
    { return NAME; }


    @Override
    public CommandArgument<?>[] getArguments()
    { return ARGUMENTS; }


    @Override
    public String getCommandDescription()
    { return DESCRIPTION; }


    @Override
    public boolean execute(CommandContext<CommandSource> context)
    {
        IFormattableTextComponent text = new StringTextComponent("Usage:").withStyle(TextFormatting.GRAY);

        for (CommandExecutor cmd : this.commandList)
        {
            text = text.append("\n/projectsarica ").withStyle(TextFormatting.GOLD);
            text = text.append(cmd.getCommandName()).withStyle(TextFormatting.DARK_AQUA);

            for (CommandArgument<?> arg : cmd.getArguments())
            {
                text = text.append(String.format(" <%s>", arg.getName())).withStyle(TextFormatting.DARK_AQUA);
            }

            text = text.append(" - ").withStyle(TextFormatting.DARK_GRAY);
            text = text.append(cmd.getCommandDescription()).withStyle(TextFormatting.GRAY);

        }

        context
            .getSource()
            .sendSuccess(text, false);

        return true;
    }


    public void addCommandToList(CommandExecutor command)
    {
        this.commandList.add(command);
    }
}
