package techtoolbox.Harbor.Actionbar;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PlayerConnection;
public class Actionbar_1_8_R2 implements Actionbar {
	
	// Creates actionbar
	public void sendActionbar(Player player, String title) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent titleComponent = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
		PacketPlayOutChat titlePacket = new PacketPlayOutChat(titleComponent, (byte) 2);
		connection.sendPacket(titlePacket);
	}
}
