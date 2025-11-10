package floris0106.rejuvenation.compat;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import de.maxhenkel.corpse.entities.CorpseEntity;
import floris0106.rejuvenation.Rejuvenation;

public class CorpseCompat
{
	@SubscribeEvent
	private static void onInteract(PlayerInteractEvent.EntityInteract event)
	{
		if (!(event.getEntity() instanceof ServerPlayer player) || !(event.getTarget() instanceof CorpseEntity corpse))
			return;

		ItemStack stack = player.getItemInHand(event.getHand());
		boolean usedItem = true;
		if (stack.is(Items.ENCHANTED_GOLDEN_APPLE) && corpse.getData(Rejuvenation.REJUVENATION_TICKS) == 0)
		{
			corpse.setData(Rejuvenation.REJUVENATION_TICKS, 400);
			corpse.playSound(SoundEvents.ZOMBIE_VILLAGER_CURE);
		}
		else if (stack.is(Items.TOTEM_OF_UNDYING) && !corpse.getData(Rejuvenation.HAS_TOTEM))
		{
			corpse.setData(Rejuvenation.HAS_TOTEM, true);
			corpse.playSound(SoundEvents.ARMOR_EQUIP_GENERIC.value());
		}
		else
			usedItem = false;

		if (usedItem)
		{
			if (!player.gameMode.isCreative())
				stack.shrink(1);

			event.setCancellationResult(InteractionResult.CONSUME);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	private static void onTick(EntityTickEvent.Pre event)
	{
		if (!(event.getEntity() instanceof CorpseEntity corpse))
			return;

		int ticks = corpse.getData(Rejuvenation.REJUVENATION_TICKS);
		if (ticks > 0)
			corpse.setData(Rejuvenation.REJUVENATION_TICKS, ticks - 1);
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	private static void onLightningStrike(EntityStruckByLightningEvent event)
	{
		if (!(event.getEntity() instanceof CorpseEntity corpse) ||
			!(corpse.level() instanceof ServerLevel level) ||
			corpse.getData(Rejuvenation.REJUVENATION_TICKS) == 0 ||
			!corpse.getData(Rejuvenation.HAS_TOTEM))
			return;

		ServerPlayer player = level.getServer().getPlayerList().getPlayer(corpse.getDeath().getPlayerUUID());
		if (player == null || player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR)
			return;

		corpse.setData(Rejuvenation.REJUVENATION_TICKS, 0);
		corpse.setData(Rejuvenation.HAS_TOTEM, false);

		player.teleportTo(level, corpse.getX(), corpse.getY(), corpse.getZ(), corpse.getYRot(), 0.0f);
		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
		player.setHealth(player.getMaxHealth());
		player.setGameMode(GameType.SURVIVAL);
	}
}