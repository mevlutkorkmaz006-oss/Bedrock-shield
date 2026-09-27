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

        // Oyuncu eğiliyorsa VE sol elde kalkan varsa
        if (player.isSneaking() && player.getOffHandStack().isOf(Items.SHIELD)) {
            // Tıklama (sol tık vurma veya sağ tık blok koyma) anında
            if (player.handSwinging) {
                // Kalkanı anlık indir (Bedrock gibi kesinti sağla)
                player.clearActiveItem();
            } else if (!player.isUsingItem()) {
                // Tıklama yoksa kalkanı kaldırmaya devam et
                player.setCurrentHand(Hand.OFF_HAND);
            }
        }
    }
}
