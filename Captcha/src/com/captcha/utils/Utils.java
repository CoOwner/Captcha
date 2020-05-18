package com.captcha.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.captcha.Main;
import com.cloutteam.samjakob.gui.ItemBuilder;
import com.cloutteam.samjakob.gui.buttons.GUIButton;
import com.cloutteam.samjakob.gui.types.PaginatedGUI;

import net.md_5.bungee.api.ChatColor;

public class Utils {
	
	private static HashMap<Player, Integer> captchaCount = new HashMap<Player, Integer>();
	
	public static String formatSeconds(long seconds) {
		long days = seconds / (24 * 60 * 60);
		seconds %= 24 * 60 * 60;
		long hh = seconds / (60 * 60);
		seconds %= 60 * 60;
		long mm = seconds / 60;
		seconds %= 60;
		long ss = seconds;
		
		if (days > 0) {
			return days + "d " + hh + "h " + mm + "m " + ss + "s";
		}
		if (hh > 0) {
			return hh + "h " + mm + "m " + ss + "s";
		}
		if (mm > 0) {
			return mm + "m " + ss + "s";
		}
		return ss + "s";
	}
	
	public static String replace(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(replace(message));
	}
	
	public static void bMsg(String message) {
		for (Player p : Bukkit.getOnlinePlayers()) { sendMessage(p, message); }
	}
	
	public static void sendConsoleMessage(String message) {
		Main.get().getServer().getConsoleSender().sendMessage(replace(message));
	}
	
	public static String replace1(String message) {
		return message.replaceAll("&", "\u00A7");
	}
	
	public static void startCaptcha(Player player) {
		int random = ThreadLocalRandom.current().nextInt(1, 4);
		String name = null;
		if (!captchaCount.containsKey(player)) {
			if (random == 1) name = Utils.replace("&cClick the Red glass &e(0)");
			if (random == 2) name = Utils.replace("&9Click the Blue glass &e(0)");
			if (random == 3) name = Utils.replace("&aClick the Green glass &e(0)");
		}
		else {
			if (captchaCount.get(player).equals(0)) {
				if (random == 1) name = Utils.replace("&cClick the Red glass &e(1)");
				if (random == 2) name = Utils.replace("&9Click the Blue glass &e(1)");
				if (random == 3) name = Utils.replace("&aClick the Green glass &e(1)");
			}
			if (captchaCount.get(player).equals(1)) {
				if (random == 1) name = Utils.replace("&cClick the Red glass &e(2)");
				if (random == 2) name = Utils.replace("&9Click the Blue glass &e(2)");
				if (random == 3) name = Utils.replace("&aClick the Green glass &e(2)");
			}
			if (captchaCount.get(player).equals(2)) {
				if (random == 1) name = Utils.replace("&cClick the Red glass &e(3)");
				if (random == 2) name = Utils.replace("&9Click the Blue glass &e(3)");
				if (random == 3) name = Utils.replace("&aClick the Green glass &e(3)");
			}
		}
		PaginatedGUI captcha = new PaginatedGUI(name);
		
		GUIButton red = new GUIButton(ItemBuilder.start(Material.STAINED_GLASS).data((short)14).name(Utils.replace("&cRed")).build());
		red.setListener(event -> {
			event.setCancelled(true);
			if (event.getRawSlot() < event.getInventory().getSize()) {
				if (random == 1) {
					captcha.getInventory().clear();
					if (!captchaCount.containsKey(player)) {
						captchaCount.put(player, 0);
						player.closeInventory();
						startCaptcha(player);
						return;
					}
					else {
						if (captchaCount.get(player).equals(0)) {
							captchaCount.put(player, 1);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(1)) {
							captchaCount.put(player, 2);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(2)) {
							captchaCount.remove(player);
							List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
							List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
							unverified.remove(player.getUniqueId().toString());
							Main.get().getCustomData().set("Unverified-by-captcha", unverified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							verified.add(player.getUniqueId().toString());
							Main.get().getCustomData().set("Verified-by-captcha", verified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							player.closeInventory();
							Utils.sendMessage(player, "&aSuccessfully solved captcha, you may interact now.");
							return;
						}
					}
				}
				else {
					captchaCount.remove(player);
					player.kickPlayer(Utils.replace("&cIncorrectly solved captcha."));
				}
			}
		});
		GUIButton blue = new GUIButton(ItemBuilder.start(Material.STAINED_GLASS).data((short)11).name(Utils.replace("&9Blue")).build());
		blue.setListener(event -> {
			event.setCancelled(true);
			if (event.getRawSlot() < event.getInventory().getSize()) {
				if (random == 2) {
					captcha.getInventory().clear();
					if (!captchaCount.containsKey(player)) {
						captchaCount.put(player, 0);
						player.closeInventory();
						startCaptcha(player);
						return;
					}
					else {
						if (captchaCount.get(player).equals(0)) {
							captchaCount.put(player, 1);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(1)) {
							captchaCount.put(player, 2);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(2)) {
							captchaCount.remove(player);
							List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
							List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
							unverified.remove(player.getUniqueId().toString());
							Main.get().getCustomData().set("Unverified-by-captcha", unverified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							verified.add(player.getUniqueId().toString());
							Main.get().getCustomData().set("Verified-by-captcha", verified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							player.closeInventory();
							Utils.sendMessage(player, "&aSuccessfully solved captcha, you may interact now.");
							return;
						}
					}
				}
				else {
					captchaCount.remove(player);
					player.kickPlayer(Utils.replace("&cIncorrectly solved captcha."));
				}
			}
		});
		GUIButton green = new GUIButton(ItemBuilder.start(Material.STAINED_GLASS).data((short)5).name(Utils.replace("&aGreen")).build());
		green.setListener(event -> {
			event.setCancelled(true);
			if (event.getRawSlot() < event.getInventory().getSize()) {
				if (random == 3) {
					captcha.getInventory().clear();
					if (!captchaCount.containsKey(player)) {
						captchaCount.put(player, 0);
						player.closeInventory();
						startCaptcha(player);
						return;
					}
					else {
						if (captchaCount.get(player).equals(0)) {
							captchaCount.put(player, 1);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(1)) {
							captchaCount.put(player, 2);
							player.closeInventory();
							startCaptcha(player);
							return;
						}
						if (captchaCount.get(player).equals(2)) {
							captchaCount.remove(player);
							List<String> unverified = (List<String>) Main.get().getCustomData().getStringList("Unverified-by-captcha");
							List<String> verified = (List<String>) Main.get().getCustomData().getStringList("Verified-by-captcha");
							unverified.remove(player.getUniqueId().toString());
							Main.get().getCustomData().set("Unverified-by-captcha", unverified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							verified.add(player.getUniqueId().toString());
							Main.get().getCustomData().set("Verified-by-captcha", verified);
							Main.get().saveConfig();
							Main.get().reloadConfig();
							player.closeInventory();
							Utils.sendMessage(player, "&aSuccessfully solved captcha, you may interact now.");
							return;
						}
					}
				}
				else {
					captchaCount.remove(player);
					player.kickPlayer(Utils.replace("&cIncorrectly solved captcha."));
				}
			}
		});
		
		boolean pickAdded = false;
		boolean pickBeingAdded = false;
		String pick = null;
		if (random == 1) pick = "red";
		if (random == 2) pick = "blue";
		if (random == 3) pick = "green";
		
		for (int i = 0; i < 54; i++) {
			int color = ThreadLocalRandom.current().nextInt(1, 3);
			int picker = ThreadLocalRandom.current().nextInt(1, 8);
			
			if (pickBeingAdded && !pickAdded) {
				if (pick == "red") captcha.addButton(red);
				if (pick == "blue") captcha.addButton(blue);
				if (pick == "green") captcha.addButton(green);
				pickAdded = true;
			}
			if (pickAdded) {
				if (pick == "red") {
					if (color == 1) captcha.addButton(blue);
					if (color == 2) captcha.addButton(green);
				}
				if (pick == "blue") {
					if (color == 1) captcha.addButton(red);
					if (color == 2) captcha.addButton(green);
				}
				if (pick == "green") {
					if (color == 1) captcha.addButton(red);
					if (color == 2) captcha.addButton(blue);
				}
			}
			else {
				if (pick == "red") {
					if (color == 1) captcha.addButton(blue);
					if (color == 2) captcha.addButton(green);
				}
				if (pick == "blue") {
					if (color == 1) captcha.addButton(red);
					if (color == 2) captcha.addButton(green);
				}
				if (pick == "green") {
					if (color == 1) captcha.addButton(red);
					if (color == 2) captcha.addButton(blue);
				}
				if (picker == 5 && !pickBeingAdded && !pickAdded) pickBeingAdded = true; 
			}
		}
		player.openInventory(captcha.getInventory());
	}
	
}
