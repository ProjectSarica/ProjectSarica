package me.ci.project.sarica.registry;

import java.util.function.Supplier;
import me.ci.project.sarica.ProjectSarica;
import me.ci.project.sarica.entities.npc.NPCEntity;
import me.ci.project.sarica.entities.npc.NPCRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ProjectSarica.MOD_ID, bus = Bus.MOD)
public final class ModEntities
{
	public static final Lazy<EntityType<NPCEntity>> NPC = entity("npc", EntityType.Builder
		.of(NPCEntity::new, EntityClassification.MISC)
		.sized(0.6f, 1.85f)
		.canSpawnFarFromPlayer());

	public static final Lazy<AttributeModifierMap> NPC_ATTRIBUTES = attributes(() -> MobEntity
		.createMobAttributes()
		.add(Attributes.MOVEMENT_SPEED, 0.25)
		.add(Attributes.ATTACK_DAMAGE, 0.5)
		.add(Attributes.FOLLOW_RANGE, 256));


	private static Lazy<AttributeModifierMap> attributes(Supplier<MutableAttribute> attributes)
	{
		return Lazy.of(() -> attributes.get().build());
	}


	private static <T extends Entity> Lazy<EntityType<T>> entity(String name, EntityType.Builder<T> builder)
	{
		return Lazy.of(() ->
		{
			ResourceLocation resource = new ResourceLocation(ProjectSarica.MOD_ID, name);
			EntityType<T> entityType = builder.build(resource.toString());
			entityType.setRegistryName(resource);
			return entityType;
		});
	}


	@SubscribeEvent
	public static void register(Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll(NPC.get());
	}


	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event)
	{
		event.put(NPC.get(), NPC_ATTRIBUTES.get());
	}


	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerRenderers(FMLClientSetupEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.NPC.get(), NPCRenderer::new);
	}


	private ModEntities()
	{}

}
