package me.ci.project.sarica.commands.base;

import com.mojang.brigadier.arguments.ArgumentType;

public class CommandArgument<T>
{
    private final String name;
    private final ArgumentType<T> argumentType;


    public CommandArgument(String name, ArgumentType<T> argumentType)
    {
        this.name = name;
        this.argumentType = argumentType;
    }


    public String getName()
    { return this.name; }


    public ArgumentType<T> getArgumentType()
    { return this.argumentType; }

}
