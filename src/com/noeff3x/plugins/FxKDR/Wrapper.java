/**
 * @author Robby Duke
 * @date Mar 10, 2013
 * @file Wrapper.java
 * @project FxKDR
 * 
 * Copyright 2013 Robby Duke Code & Design
 *
 * Email: robby@noeff3x.com
 * Skype: rduke56
 */


package com.noeff3x.plugins.FxKDR;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class Wrapper extends JavaPlugin {
    @Override
    public void onEnable() {
	this.saveDefaultConfig();
	init_Config();

	try {
	    Config.sql = DriverManager.getConnection("jdbc:mysql://" + Config.SQL_Host + "/" + Config.SQL_Data + "?user=" + Config.SQL_User + "&password=" + Config.SQL_Pass);
	} catch (SQLException e) {
	    getLogger().info(e.getMessage());
	}

	getCommand("kd").setExecutor(new KillDeath());
	
	Config.server.getPluginManager().registerEvents(new StatLogger(), this);
    }

    @Override
    public void onDisable() {
	try {
	    Config.sql.close();
	} catch (SQLException e) {
	    getLogger().info(e.getMessage());
	}
    }

    public void init_Config() {
	Configuration config = this.getConfig();

	Config.server = Bukkit.getServer();

	Config.SQL_Host = config.getString("SQL-Host");
	Config.SQL_User = config.getString("SQL-User");
	Config.SQL_Pass = config.getString("SQL-Pass");
	Config.SQL_Data = config.getString("SQL-Data");

	Config.message_Prefix = Functions.formatMessage("&8[&6&oFxKDR&8] &f");
    }
}
