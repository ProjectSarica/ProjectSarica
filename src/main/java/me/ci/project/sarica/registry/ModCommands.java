package me.ci.project.sarica.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.ci.project.sarica.ProjectSarica;
import me.ci.project.sarica.commands.HelpCommand;
import me.ci.project.sarica.commands.base.CommandArgument;
import me.ci.project.sarica.commands.base.CommandExecutor;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ProjectSarica.MOD_ID, bus = Bus.FORGE)
public final class ModCommands
{
    public static final HelpCommand HELP = new HelpCommand();


    private static Command<CommandSource> errorHandler(CommandExecutorResponse command)
    {
        //@formatter:off
        return context ->
        {
            try { return command.execute(context) ? 1 : 0; }
            catch (Exception e)
            {
                context
                    .getSource()
                    .sendFailure(new StringTextComponent("An internal error has occured while executing this command! Please see console for more information.")
                        .withStyle(TextFormatting.RED));

                ProjectSarica.LOGGER.error("Failed to execute command!", e);
                return 0;
            }
        };
        //@formatter:on
    }


    private static LiteralArgumentBuilder<CommandSource> buildSubcommand(CommandExecutor executor)
    {
        LiteralArgumentBuilder<CommandSource> subcmd = Commands
            .literal(executor.getCommandName())
            .requires(source -> source.hasPermission(executor.requiresOp() ? 1 : 0));

        addArguments(subcmd, executor, 0);
        HELP.addCommandToList(executor);
        return subcmd;
    }


    private static LiteralArgumentBuilder<CommandSource> addArguments(LiteralArgumentBuilder<CommandSource> command, CommandExecutor executor, int index)
    {
        CommandArgument<?>[] argList = executor.getArguments();

        if (argList.length <= index) return command.executes(errorHandler(executor::execute));

        CommandArgument<?> arg = argList[index];
        RequiredArgumentBuilder<CommandSource, ?> subcmd = Commands
            .argument(arg.getName(), arg.getArgumentType())
            .then(addArguments(command, executor, index + 1));

        return command.then(subcmd);
    }


    @SubscribeEvent
    public static void register(RegisterCommandsEvent event)
    {
        LiteralArgumentBuilder<CommandSource> cmd = Commands.literal("projectsarica");

        cmd.then(buildSubcommand(HELP));

        cmd.executes(errorHandler(HELP::execute));
        event.getDispatcher().register(cmd);
    }


    private static interface CommandExecutorResponse
    {
        boolean execute(CommandContext<CommandSource> context);
    }


    private ModCommands()
    {}
}
