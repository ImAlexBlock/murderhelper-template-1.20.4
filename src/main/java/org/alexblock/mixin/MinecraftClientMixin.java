package org.alexblock.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        // 将原先的成员变量转换为局部变量
        String additionalText = " - MurderHelper 1.0";

        // 获取原始方法的返回值
        String originalTitle = cir.getReturnValue();

        // 追加局部变量的值
        String modifiedTitle = originalTitle + additionalText;

        // 设置新的返回值
        cir.setReturnValue(modifiedTitle);
    }
}
