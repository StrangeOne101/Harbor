package techtoolbox.Harbor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import techtoolbox.Harbor.Actionbar.Actionbar;
import techtoolbox.Harbor.Actionbar.Actionbar_1_10_R1;
import techtoolbox.Harbor.Actionbar.Actionbar_1_11_R1;
import techtoolbox.Harbor.Actionbar.Actionbar_1_12_R1;
import techtoolbox.Harbor.Actionbar.Actionbar_1_13_R1;
import techtoolbox.Harbor.Actionbar.Actionbar_1_13_R2;
import techtoolbox.Harbor.Actionbar.Actionbar_1_8_R1;
import techtoolbox.Harbor.Actionbar.Actionbar_1_8_R2;
import techtoolbox.Harbor.Actionbar.Actionbar_1_8_R3;
import techtoolbox.Harbor.Actionbar.Actionbar_1_9_R1;
public class Main extends JavaPlugin implements Listener {
	
	public static int amountSleeping; 
	public int sleepingCheck; 
	public static String sleepingMessage;
	public static String allSleepingMessage;
	public String nightSkippedMessage;
	public String playerSleepingMessage;
	public Boolean actionBarSwitch;
	public Boolean chatSwitch;
	public Boolean skipNightSwitch;
	
	// NMS interface reference for actionbar
	public static Actionbar actionbar;
	
	public void onEnable() {
		this.saveDefaultConfig();
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
		
		// Attempt to enable NMS
		if (setupActionbar()) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
        	String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        	System.out.println("[Harbor] Server version " + version + " isn't compatible with Harbor.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
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
		
		// Fix sleeping number if server reloaded while a player was in bed
		if (amountSleeping < 0) {
			amountSleeping = 0;
		}
	}
	
	@EventHandler 
	public void onLeaveBed(PlayerBedLeaveEvent event) {
		amountSleeping--;
	}
	
	// Sets up actionbar NMS for specific versions
    private boolean setupActionbar() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        System.out.println("[Harbor] Running on version " + version + ".");
        // 1.8- 1.8.2
        if (version.equals("v1_8_R1")) {
            actionbar = new Actionbar_1_8_R1();
        }
        // 1.8.3
        else if (version.equals("v1_8_R2")) {
            actionbar = new Actionbar_1_8_R2();
        }
        // 1.8.4 - 1.8.9
        else if (version.equals("v1_8_R3")) {
            actionbar = new Actionbar_1_8_R3();
        }
        // 1.9 - 1.9.2
        else if (version.equals("v1_9_R1")) {
            actionbar = new Actionbar_1_9_R1();
        }
        // 1.10 - 1.10.2
        else if (version.equals("v1_10_R1")) {
            actionbar = new Actionbar_1_10_R1();
        }
        // 1.11 - 1.11.2
        else if (version.equals("v1_11_R1")) {
            actionbar = new Actionbar_1_11_R1();
        }
        // 1.12 - 1.12.2
        else if (version.equals("v1_12_R1")) {
            actionbar = new Actionbar_1_12_R1();
        } 
        // 1.13
        else if (version.equals("v1_13_R1")) {
            actionbar = new Actionbar_1_13_R1();
        } 
        // 1.13.1
        else if (version.equals("v1_13_R2")) {
        	actionbar = new Actionbar_1_13_R2();
        }
        return actionbar != null;
    }
}
