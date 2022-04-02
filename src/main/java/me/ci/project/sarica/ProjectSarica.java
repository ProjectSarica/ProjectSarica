package me.ci.project.sarica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import me.ci.project.sarica.registry.ModEntities;
import me.ci.project.sarica.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ProjectSarica.MOD_ID)
public class ProjectSarica
{
    public static final String MOD_ID = "projectsarica";
    public static final String MOD_NAME = "Project Sarica";
    public static final Logger LOGGER = LogManager.getLogger(ProjectSarica.MOD_ID);


    public ProjectSarica()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(ModEntities.class);
        bus.register(ModItems.class);
    }

}
