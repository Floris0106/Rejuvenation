package floris0106.rejuvenation;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Objects;

import floris0106.rejuvenation.config.Config;

@EventBusSubscriber(modid = Rejuvenation.MODID)
public class EventHandler
{
	@SubscribeEvent
	private static void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		if (event.isEndConquered() || !(event.getEntity() instanceof ServerPlayer player))
			return;

		AttributeInstance attribute = Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH));
		double maxHealth = attribute.getBaseValue();
		if (!player.gameMode.isSurvival())
			return;

		maxHealth -= Config.HEALTH_LOST.getAsDouble();
		if (maxHealth <= 0.0f)
		{
			maxHealth = Config.RESPAWN_HEALTH.getAsDouble();
			player.setGameMode(GameType.SPECTATOR);
		}

		attribute.setBaseValue(maxHealth);
	}

	@SubscribeEvent
	private static void onUseTotem(LivingUseTotemEvent event)
	{
		if (!(event.getEntity() instanceof ServerPlayer player) || !player.hasEffect(Rejuvenation.REJUVENATION_EFFECT))
			return;

		AttributeInstance attribute = Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH));
		attribute.setBaseValue(Math.min(attribute.getBaseValue() + Config.HEALTH_GAINED.getAsDouble(), Config.MAX_HEALTH.getAsDouble()));
	}
}