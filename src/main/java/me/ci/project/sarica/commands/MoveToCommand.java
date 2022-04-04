package me.ci.project.sarica.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ci.project.sarica.entities.npc.NPCEntity;
import me.ci.project.sarica.mio.pathfinding.MoveToPositionGoal;
import me.ci.project.sarica.util.cmd.CommandExecutor;
import me.ci.project.sarica.util.cmd.TypedEntityArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class MoveToCommand implements CommandExecutor
{
	public static final String NAME = "moveto";
	public static final String DESCRIPTION = "Instructs an npc to move to a target location.";
	public static final String[] ARGUMENTS = { "npc", "x", "y", "z" };


	@Override
	public boolean execute(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		NPCEntity npc = TypedEntityArgument.getTypedEntity(context, "npc", NPCEntity.class);
		BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "position");

		MoveToPositionGoal action = new MoveToPositionGoal(npc.getAgent(), pos);

		try
		{
			npc.getAgent().setAction(action);
			return true;
		}
		catch (IllegalStateException e)
		{
			ITextComponent message = new TranslationTextComponent("command.projectsarica.moveto.action_not_cancelable")
				.withStyle(TextFormatting.RED);
			context.getSource().sendFailure(message);
			return false;
		}

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
