/**
 * @author Robby Duke
 * @date Mar 10, 2013
 * @file Functions.java
 * @project FxKDR
 * 
 * Copyright 2013 Robby Duke Code & Design
 *
 * Email: robby@noeff3x.com
 * Skype: rduke56
 */

package com.noeff3x.plugins.FxKDR;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Functions {
    public static String formatMessage(String data) {
	return data.replaceAll("&([0-9a-fk-or])", "¤$1");
    }

    public static double roundTwoDecimals(double d) {
	DecimalFormat twoDForm = new DecimalFormat("#.##");
	return Double.valueOf(twoDForm.format(d));
    }

    public static void setMetadata(Player player, String key, Object value, Plugin plugin) {
	player.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    public static Object getMetadata(Player player, String key, Plugin plugin) {
	List<MetadataValue> values = player.getMetadata(key);
	for (MetadataValue value : values) {
	    if (value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
		return value.value();
	    }
	}
	return values;
    }
}
