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

@EventBusSubscriber(modid = Rejuvenation.MODID)
public class EventHandler
{
	@SubscribeEvent
	private static void onClone(PlayerEvent.Clone event)
	{
		if (!(event.getEntity() instanceof ServerPlayer player))
			return;

		double maxHealth = Objects.requireNonNull(event.getOriginal().getAttribute(Attributes.MAX_HEALTH)).getBaseValue();
		if (event.isWasDeath() && player.gameMode.isSurvival())
			maxHealth -= 2.0f;

		if (maxHealth > 0.0f)
			Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealth);
		else
			player.setGameMode(GameType.SPECTATOR);
	}

	@SubscribeEvent
	private static void onUseTotem(LivingUseTotemEvent event)
	{
		if (!(event.getEntity() instanceof ServerPlayer player) || !player.hasEffect(Rejuvenation.REJUVENATION_EFFECT))
			return;

		AttributeInstance maxHealthAttribute = Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH));
		maxHealthAttribute.setBaseValue(Math.min(maxHealthAttribute.getBaseValue() + 2.0f, 20.0f));
	}
}