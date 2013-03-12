/**
 * @author Robby Duke
 * @date Mar 11, 2013
 * @file KillDeath.java
 * @project FxKDR
 * 
 * Copyright 2013 Robby Duke Code & Design
 *
 * Email: robby@noeff3x.com
 * Skype: rduke56
 */

package com.noeff3x.plugins.FxKDR;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillDeath implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	try {
	    if(cmd.getName().equalsIgnoreCase("kd") && args.length > 0) {
		if(args[0].equalsIgnoreCase("set") && args.length == 4) {
		    if(!sender.hasPermission("fxkdr.stats.modify")) {
			sender.sendMessage(Config.message_Prefix + "Sorry, you don't have enough permission to do this.");
			return true;
		    }

		    if(args[1].equalsIgnoreCase("kills")) {
			PreparedStatement setKills = Config.sql.prepareStatement("update `players` set `kills`=? where `player`=?");

			setKills.setInt(1, Integer.parseInt(args[3]));
			setKills.setString(2, args[2]);

			int affected = setKills.executeUpdate();
			setKills.close();

			if(affected == 0) {
			    PreparedStatement insertKills = Config.sql.prepareStatement("insert into `players` ( `deaths`, `player`, `staff_Kills`, `kills`) values ( '0', ?, '0', ?)");

			    insertKills.setString(1, args[2]);
			    insertKills.setInt(2, Integer.parseInt(args[3]));

			    insertKills.execute();
			    insertKills.close();
			}

			sender.sendMessage(Config.message_Prefix + "Stat's updated.");
			return true;
		    }

		    if(args[1].equalsIgnoreCase("deaths")) {
			PreparedStatement setDeaths = Config.sql.prepareStatement("update `players` set `deaths`=? where `player`=?");

			setDeaths.setInt(1, Integer.parseInt(args[3]));
			setDeaths.setString(2, args[2]);

			int affected = setDeaths.executeUpdate();
			setDeaths.close();

			if(affected == 0) {
			    PreparedStatement insertKills = Config.sql.prepareStatement("insert into `players` ( `deaths`, `player`, `staff_Kills`, `kills`) values ( ?, ?, '0', '0')");

			    insertKills.setInt(1, Integer.parseInt(args[3]));
			    insertKills.setString(2, args[2]);

			    insertKills.execute();
			    insertKills.close();
			}

			sender.sendMessage(Config.message_Prefix + "Stat's updated.");
			return true;
		    }

		    if(args[1].equalsIgnoreCase("staffkills")) {
			PreparedStatement setKills = Config.sql.prepareStatement("update `players` set `staff_Kills`=? where `player`=?");

			setKills.setInt(1, Integer.parseInt(args[3]));
			setKills.setString(2, args[2]);

			int affected = setKills.executeUpdate();
			setKills.close();

			if(affected == 0) {
			    PreparedStatement insertKills = Config.sql.prepareStatement("insert into `players` ( `deaths`, `player`, `staff_Kills`, `kills`) values ( '0', ?, ?, ?)");

			    insertKills.setString(1, args[2]);
			    insertKills.setInt(2, Integer.parseInt(args[3]));
			    insertKills.setInt(3, Integer.parseInt(args[3]));

			    insertKills.execute();
			    insertKills.close();
			}

			sender.sendMessage(Config.message_Prefix + "Stat's updated.");
			return true;
		    }
		}

		if(args[0].equalsIgnoreCase("reset") && args.length == 2) {
		    if(!sender.hasPermission("fxkdr.stats.reset")) {
			sender.sendMessage(Config.message_Prefix + "Sorry, you don't have enough permission to do this.");
			return true;
		    }
		    PreparedStatement resetQuery = Config.sql.prepareStatement("update `players` set `deaths`='0', `staff_Kills`='0', `kills`='0' where `player`=?");

		    resetQuery.setString(1, args[1]);

		    resetQuery.execute();
		    resetQuery.close();

		    sender.sendMessage(Config.message_Prefix + args[1] + Functions.formatMessage("'s stats have been &breset&f!"));

		    return true;
		} else if(args[0].equalsIgnoreCase("reset") && args.length == 1) {
		    return false;
		}

		if(args.length == 1) {
		    PreparedStatement grabKD = Config.sql.prepareStatement("select `kills`, `deaths`, `staff_Kills` from `players` where `player` = ?  limit 1");

		    grabKD.setString(1, args[0]);

		    ResultSet kdValue = grabKD.executeQuery();

		    if(!kdValue.isBeforeFirst()) {
			sender.sendMessage(Config.message_Prefix + args[0] + " has no kills OR deaths! Go pwn them (:<");
			return true;
		    }

		    kdValue.next();

		    int kills = kdValue.getInt(1);
		    int deaths = kdValue.getInt(2);
		    int staff_Kills = kdValue.getInt(3);
		    double ratio = kills;

		    if(deaths != 0) {
			ratio = Functions.roundTwoDecimals((double)kills / deaths);
		    }

		    kdValue.close();
		    grabKD.close();

		    sender.sendMessage(Config.message_Prefix + Functions.formatMessage(args[0] + "'s kills: &b&o" + kills));
		    sender.sendMessage(Config.message_Prefix + Functions.formatMessage(args[0] + "'s staff kills: &b&o" + Integer.toString(staff_Kills)));
		    sender.sendMessage(Config.message_Prefix + Functions.formatMessage(args[0] + "'s deaths: &b&o" + deaths));
		    sender.sendMessage(Config.message_Prefix + Functions.formatMessage(args[0] + "'s ration: &b&o" + ratio));

		    return true;
		}
	    } else if(cmd.getName().equalsIgnoreCase("kd") && args.length == 0) {
		PreparedStatement grabKD = Config.sql.prepareStatement("select `kills`, `deaths`, `staff_Kills` from `players` where `player` = ?  limit 1");

		grabKD.setString(1, sender.getName());

		ResultSet kdValue = grabKD.executeQuery();

		if(!kdValue.isBeforeFirst()) {
		    sender.sendMessage(Config.message_Prefix + "You have no kills OR deaths! Why not... kill yourself? (:");
		    return true;
		}

		kdValue.next();

		int kills = kdValue.getInt(1);
		int deaths = kdValue.getInt(2);
		int staff_Kills = kdValue.getInt(3);
		double ratio = kills;

		if(deaths != 0) {
		    ratio = Functions.roundTwoDecimals((double)kills / deaths);
		}

		kdValue.close();
		grabKD.close();

		sender.sendMessage(Config.message_Prefix + Functions.formatMessage("Kills: &b&o" + Integer.toString(kills)));
		sender.sendMessage(Config.message_Prefix + Functions.formatMessage("Staff Kills: &b&o" + Integer.toString(staff_Kills)));
		sender.sendMessage(Config.message_Prefix + Functions.formatMessage("Deaths: &b&o" + Integer.toString(deaths)));
		sender.sendMessage(Config.message_Prefix + Functions.formatMessage("Ration: &b&o" + Double.toString(ratio)));

		return true;
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return false;
    }
}
