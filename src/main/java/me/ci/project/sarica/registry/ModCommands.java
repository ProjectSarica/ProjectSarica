package me.ci.project.sarica.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.ci.project.sarica.ProjectSarica;
import me.ci.project.sarica.commands.HelpCommand;
import me.ci.project.sarica.commands.MoveToCommand;
import me.ci.project.sarica.util.cmd.CommandBuilder;
import me.ci.project.sarica.util.cmd.TypedEntityArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ProjectSarica.MOD_ID, bus = Bus.FORGE)
public final class ModCommands
{
	public static final HelpCommand HELP = new HelpCommand();
	public static final MoveToCommand MOVE_TO = new MoveToCommand();

	public static final LiteralArgumentBuilder<CommandSource> PROJECT_SARICA = new CommandBuilder("projectsarica", HELP)
		.addSubcommand(HELP)
		.buildSubcommand()

		.addSubcommand(MOVE_TO)
		.arg("npc", TypedEntityArgument.typedEntity())
		.arg("position", BlockPosArgument.blockPos())
		.buildSubcommand()

		.buildNamespace();


	@SubscribeEvent
	public static void register(RegisterCommandsEvent event)
	{
		event.getDispatcher().register(PROJECT_SARICA);
	}


	private ModCommands()
	{}
}
