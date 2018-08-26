package techtoolbox.Harbor;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R1.IChatBaseComponent;
import net.minecraft.server.v1_13_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R1.PacketPlayOutTitle.EnumTitleAction;
import techtoolbox.Harbor.ActionBarMessage;
import net.minecraft.server.v1_13_R1.PlayerConnection;
public class Main extends JavaPlugin implements Listener {
	
	public static int amountSleeping; 
	public static int sleepingCheck; 
	public static String sleepingMessage;
	public static String allSleepingMessage;
	public static String nightSkippedMessage;
	public static String playerSleepingMessage;
	public static Boolean actionBarSwitch;
	public static Boolean chatSwitch;
	public static Boolean skipNightSwitch;
	
	public void onEnable() {
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		this.getConfig();
		sleepingCheck = getConfig().getInt("values.sleepingCheck");
		sleepingMessage = getConfig().getString("messages.actionbar.sleepingMessage");
		allSleepingMessage = getConfig().getString("messages.actionbar.allSleepingMessage");
		nightSkippedMessage = getConfig().getString("messages.chat.nightSkippedMessage");
		playerSleepingMessage = getConfig().getString("messages.chat.playerSleepingMessage");
		actionBarSwitch = getConfig().getBoolean("messages.actionbar.actionbarSwitch");
		chatSwitch = getConfig().getBoolean("messages.chat.chatSwitch");
		skipNightSwitch = getConfig().getBoolean("features.skipnight");
		
		// Enable modules
		if (actionBarSwitch) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarMessage(), 0L, (long)(sleepingCheck * 20));
		}
		
		// Reset sleep counter
		amountSleeping = 0;
	}
	
	@EventHandler
	public void onSleep(PlayerBedEnterEvent event) {
		amountSleeping++;
		
		// Broadcast who went to bed if chat messages are enabled
		if (chatSwitch) {
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', playerSleepingMessage.replace("[player]", event.getPlayer().getName()).replace("[sleeping]", String.valueOf(amountSleeping)).replace("[online]", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))));
		}
		
		// Skip night if module
		if (skipNightSwitch && amountSleeping > Bukkit.getServer().getOnlinePlayers().size() / 2) {
			Bukkit.getServer().getWorld(this.getConfig().getString("values.worldname")).setTime(1000);
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nightSkippedMessage));
		}
	}
	@EventHandler 
	public void onLeaveBed(PlayerBedLeaveEvent event) {
		amountSleeping--;
	}
	
	// Creates actionbar
	public static void sendTitle(Player player, String title) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent titleComponent = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
	    PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, titleComponent);
	    connection.sendPacket(titlePacket);
	}
}
