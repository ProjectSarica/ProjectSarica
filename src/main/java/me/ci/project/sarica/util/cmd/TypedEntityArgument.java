package me.ci.project.sarica.util.cmd;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TranslationTextComponent;

public class TypedEntityArgument extends EntityArgument
{
	public static <T extends Entity> T getTypedEntity(CommandContext<CommandSource> context, String argName, Class<T> type) throws CommandSyntaxException
	{
		Entity entity = getEntity(context, argName);
		if (type.isInstance(entity)) return type.cast(entity);

		String entityType = type.getSimpleName().replace("Entity", "");
		throw new SimpleCommandExceptionType( //
			new TranslationTextComponent("argument.projectsarica.entity.wrong_type", entityType))
			.create();
	}


	public static TypedEntityArgument typedEntity()
	{
		return new TypedEntityArgument();
	}


	protected TypedEntityArgument()
	{
		super(true, false);
	}
}
