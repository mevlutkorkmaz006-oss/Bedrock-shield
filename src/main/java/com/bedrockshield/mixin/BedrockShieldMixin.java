package com.bedrockshield.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class BedrockShieldMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // Eğilirken ve sol elde kalkan varken
        if (player.isSneaking() && player.getOffHandStack().isOf(Items.SHIELD)) {
            // El sallama/vuruş animasyonu varsa veya aktif item kullanılıyorsa kalkan engellemesini kaldır
            if (player.handSwinging) {
                if (player.isUsingItem() && player.getActiveHand() == Hand.OFF_HAND) {
                    player.clearActiveItem();
                }
            } else if (!player.isUsingItem()) {
                player.setCurrentHand(Hand.OFF_HAND);
            }
        }
    }
}
