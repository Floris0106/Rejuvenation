package floris0106.rejuvenation.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import floris0106.rejuvenation.Rejuvenation;

@Mod(value = Rejuvenation.MODID, dist = Dist.CLIENT)
public class RejuvenationClient
{
	public RejuvenationClient(ModContainer container)
	{
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}
}