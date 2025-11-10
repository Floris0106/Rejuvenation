package floris0106.rejuvenation;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import org.slf4j.Logger;

import floris0106.rejuvenation.compat.CorpseCompat;
import floris0106.rejuvenation.config.Config;
import floris0106.rejuvenation.effect.RejuvenationMobEffect;

@Mod(Rejuvenation.MODID)
public class Rejuvenation
{
	public static final String MODID = "rejuvenation";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> REJUVENATION_TICKS = ATTACHMENT_TYPES.register("rejuvenation_ticks", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> HAS_TOTEM = ATTACHMENT_TYPES.register("has_totem", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);
	public static final DeferredHolder<MobEffect, RejuvenationMobEffect> REJUVENATION_EFFECT = MOB_EFFECTS.register("rejuvenation", RejuvenationMobEffect::new);

	public Rejuvenation(IEventBus modEventBus, ModContainer modContainer)
	{
		ATTACHMENT_TYPES.register(modEventBus);
		MOB_EFFECTS.register(modEventBus);

		if (ModList.get().isLoaded("corpse"))
			modEventBus.addListener((FMLCommonSetupEvent event) ->
			{
				if (Config.ENABLE_CORPSE_COMPAT.get())
				{
					NeoForge.EVENT_BUS.register(CorpseCompat.class);
					LOGGER.info("Corpse compatibility enabled.");
				}
			});

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}