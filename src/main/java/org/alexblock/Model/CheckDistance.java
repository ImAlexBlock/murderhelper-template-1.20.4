package org.alexblock.Model;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class CheckDistance implements ClientModInitializer {
    private static String targetPlayerName = null;
    private static int tickCounter = 0;
    private static final int TICKS_INTERVAL = 5; // 每5个tick检测一次

    @Override
    public void onInitializeClient() {
        // 注册客户端每tick事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && targetPlayerName != null) {
                tickCounter++;
                if (tickCounter >= TICKS_INTERVAL) {
                    checkDistance(targetPlayerName);
                    tickCounter = 0; // 重置计数器
                }
            }
        });
    }

    // 检测距离的方法并设置目标玩家名称
    public static void checkDistance(String playerName) {
        targetPlayerName = playerName;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            // 遍历世界中的所有玩家来找到目标玩家
            for (PlayerEntity player : client.world.getPlayers()) {
                if (player.getName().getString().equals(playerName)) {
                    // 找到目标玩家后，计算与当前玩家的距离
                    if (client.player != null) {
                        Vec3d playerPos = client.player.getPos();
                        Vec3d targetPos = player.getPos();
                        double distance = playerPos.distanceTo(targetPos);
                        distance = Math.round(distance * 10.0) / 10.0;
                        // 发送距离信息给玩家
                        client.player.sendMessage(Text.literal(playerName + " 距您 " + distance + " Blocks"), true);
                    }
                    break;
                }
            }
        }
    }
}