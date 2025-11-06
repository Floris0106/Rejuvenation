package floris0106.rejuvenation;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(Rejuvenation.MODID)
public class Rejuvenation
{
	public static final String MODID = "rejuvenation";

	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> REJUVENATION_TICKS = ATTACHMENT_TYPES.register("rejuvenation_ticks", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> HAS_TOTEM = ATTACHMENT_TYPES.register("has_totem", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);
	public static final DeferredHolder<MobEffect, RejuvenationMobEffect> REJUVENATION_EFFECT = MOB_EFFECTS.register("rejuvenation", RejuvenationMobEffect::new);

	public Rejuvenation(IEventBus modEventBus, ModContainer modContainer)
	{
		ATTACHMENT_TYPES.register(modEventBus);
		MOB_EFFECTS.register(modEventBus);
	}
}