package floris0106.rejuvenation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import floris0106.rejuvenation.Rejuvenation;

@Mixin(Foods.class)
public class FoodsMixin
{
	@WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodProperties$Builder;build()Lnet/minecraft/world/food/FoodProperties;", ordinal = 19))
	private static FoodProperties rejuvenation$addRejuvenationEffect(FoodProperties.Builder instance, Operation<FoodProperties> original)
	{
		return original.call(instance.effect(() -> new MobEffectInstance(Rejuvenation.REJUVENATION_EFFECT, 400, 0), 1.0f));
	}
}