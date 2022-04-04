package me.ci.project.sarica.util.cmd;

public interface IHelpSubcommand extends CommandExecutor
{
	void addCommandToList(CommandExecutor command);
}
