package floris0106.rejuvenation.mixin.compat.corpse;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import de.maxhenkel.corpse.entities.CorpseEntity;
import floris0106.rejuvenation.compat.CorpseCompat;

@Mixin(value = CorpseEntity.class, remap = false)
public abstract class CorpseEntityMixin
{
	@WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lde/maxhenkel/corpse/entities/CorpseEntity;discard()V"))
	private boolean rejuvenation$preventDespawn(CorpseEntity corpse)
	{
		return CorpseCompat.allowCorpseDespawn(corpse);
	}
}