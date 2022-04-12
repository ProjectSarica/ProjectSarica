package me.ci.project.navi.registry;

import java.util.function.Supplier;
import me.ci.project.navi.ProjectNavi;
import me.ci.project.navi.entities.DebugZombie;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
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

@Mod.EventBusSubscriber(modid = ProjectNavi.MOD_ID, bus = Bus.MOD)
public final class ModEntities
{
	public static Lazy<EntityType<DebugZombie>> DEBUG_ZOMBIE = entity("debug_zombie", EntityType.Builder
		.of(DebugZombie::new, EntityClassification.MISC)
		.sized(0.6f, 1.85f));

	public static final Lazy<AttributeModifierMap> DEBUG_ZOMBIE_ATTRIBUTES = attributes(() -> MobEntity
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
			ResourceLocation resource = new ResourceLocation(ProjectNavi.MOD_ID, name);
			EntityType<T> entityType = builder.build(resource.toString());
			entityType.setRegistryName(resource);
			return entityType;
		});
	}


	@SubscribeEvent
	public static void register(Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll(DEBUG_ZOMBIE.get());
	}


	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event)
	{
		event.put(DEBUG_ZOMBIE.get(), DEBUG_ZOMBIE_ATTRIBUTES.get());
	}


	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerRenderers(FMLClientSetupEvent event)
	{

		RenderingRegistry
			.registerEntityRenderingHandler(DEBUG_ZOMBIE
				.get(), r -> new BipedRenderer<>(r, new BipedModel<>(0.5f), 0.3f));
	}


	private ModEntities()
	{}
}
