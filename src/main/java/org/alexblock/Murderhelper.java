package org.alexblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.alexblock.Model.CheckDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Murderhelper implements ModInitializer {
	private static final String MOD_ID = "MurderHelper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		LOGGER.info("MurderHelper by AlexBlock~");
		// 注册事件
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				detectMurderer(client.player);
			}
		});
	}

	private void detectMurderer(ClientPlayerEntity player) {
		if (player.getWorld() == null) return;
		player.getWorld().getPlayers().forEach(otherPlayer -> {
			String name = "";
			for (ItemStack itemStack : otherPlayer.getInventory().main) {
				if (itemStack.getItem() == Items.SNOWBALL) {
					LOGGER.info(otherPlayer.getName().getString() + "minecraft:snowball");
					player.sendMessage(Text.literal("[§3MurderHelper§f] " + otherPlayer.getName().getString() + " 检测到 小刀"), false);
					name = otherPlayer.getName().getString();
				} else if (itemStack.getItem() == Items.CARROT_ON_A_STICK) {
					LOGGER.info(otherPlayer.getName().getString() + "minecraft:carrot_on_a_stick");
					player.sendMessage(Text.literal("[§3MurderHelper§f] " + otherPlayer.getName().getString() + " 检测到 肾上腺素/玩家传送器"), false);
					name = otherPlayer.getName().getString();
				} else if (itemStack.getItem() == Items.STICK) {
					LOGGER.info(otherPlayer.getName().getString() + "stick");
					player.sendMessage(Text.literal("[§3MurderHelper§f] " + otherPlayer.getName().getString() + " 检测到 无辜者追踪器"), false);
					name = otherPlayer.getName().getString();
				}
				else {
					CheckDistance.checkDistance(name);
				}
			}
		});
	}
}