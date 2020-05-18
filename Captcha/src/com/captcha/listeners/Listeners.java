package com.captcha.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.captcha.Main;
import com.captcha.utils.Utils;

public class Listeners implements Listener {
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();
		List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
		List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
		if (verified.contains(player.getUniqueId().toString())) return;
		if (!unverified.contains(player.getUniqueId().toString())) unverified.add(player.getUniqueId().toString());
		Main.get().getCustomData().set("Unverified-by-captcha", unverified);
		Main.get().saveConfig();
		Main.get().reloadConfig();
	}
	
	
	@EventHandler
	public static void onChat(AsyncPlayerChatEvent event) {
		Player player = (Player) event.getPlayer();
		List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
		List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
		if (verified.contains(player.getUniqueId().toString())) return;
		if (unverified.contains(player.getUniqueId().toString())) {
			event.setCancelled(true);
			Utils.startCaptcha(player);
			
		}
		if (!unverified.contains(player.getUniqueId().toString()) && !verified.contains(player.getUniqueId().toString())) {
			event.setCancelled(true);
			Utils.startCaptcha(player);
		
		}
	}
	
	@EventHandler
	public static void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = (Player) event.getPlayer();
		List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
		List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
		if (verified.contains(player.getUniqueId().toString())) return;
		if (unverified.contains(player.getUniqueId().toString())) {
			event.setCancelled(true);
			Utils.startCaptcha(player);
			
		}
		if (!unverified.contains(player.getUniqueId().toString()) && !verified.contains(player.getUniqueId().toString())) {
			event.setCancelled(true);
			Utils.startCaptcha(player);		
		}
	}
}
