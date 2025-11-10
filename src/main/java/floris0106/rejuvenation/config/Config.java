package floris0106.rejuvenation.config;

import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static final ModConfigSpec SPEC;

	public static final ModConfigSpec.DoubleValue HEALTH_LOST;
	public static final ModConfigSpec.DoubleValue HEALTH_GAINED;
	public static final ModConfigSpec.DoubleValue MAX_HEALTH;
	public static final ModConfigSpec.DoubleValue RESPAWN_HEALTH;
	public static final ModConfigSpec.EnumValue<GameType> RESPAWN_GAMEMODE;
	public static final ModConfigSpec.BooleanValue ENABLE_CORPSE_COMPAT;

	static
	{
		HEALTH_LOST = BUILDER
			.comment("The amount of maximum health (in half-hearts) lost upon death")
			.translation("rejuvenation.config.health_lost")
			.defineInRange("healthLost", 2.0, 0.0, 1000.0);

		HEALTH_GAINED = BUILDER
			.comment("The amount of maximum health (in half-hearts) gained when using a Totem of Undying while having the Rejuvenation effect")
			.translation("rejuvenation.config.health_gained")
			.defineInRange("healthGained", 2.0, 0.0, 1000.0);

		MAX_HEALTH = BUILDER
			.comment("The maximum amount of maximum health (in half-hearts) a player can have")
			.translation("rejuvenation.config.max_health")
			.defineInRange("maxHealth", 20.0, 1.0, 1000.0);

		BUILDER
			.comment("Options related to player respawning")
			.translation("rejuvenation.config.respawn")
			.push("respawn");

		RESPAWN_HEALTH = BUILDER
			.comment("The amount of maximum health (in half-hearts) a player respawns with after being resurrected")
			.translation("rejuvenation.config.respawn.health")
			.defineInRange("health", 20.0, 1.0, 1000.0);

		RESPAWN_GAMEMODE = BUILDER
			.comment("The gamemode a player respawns in when resurrected")
			.translation("rejuvenation.config.respawn.gamemode")
			.defineEnum("gamemode", GameType.SURVIVAL);

		BUILDER.pop();

		BUILDER
			.comment("Options related to mod compatibility")
			.translation("rejuvenation.config.compat")
			.push("compat");

		BUILDER
			.comment("Corpse compatibility options")
			.translation("rejuvenation.config.compat.corpse")
			.push("corpse");

		ENABLE_CORPSE_COMPAT = BUILDER.gameRestart()
			.comment("Enable compatibility with the Corpse mod (ignored if Corpse isn't installed)")
			.translation("rejuvenation.config.compat.corpse.enable")
			.define("enable", true);

		BUILDER.pop(2);

		SPEC = BUILDER.build();
	}
}
