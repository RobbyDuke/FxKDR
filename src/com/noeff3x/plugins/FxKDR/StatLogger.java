/**
 * @author Robby Duke
 * @date Mar 11, 2013
 * @file StatLogger.java
 * @project FxKDR
 * 
 * Copyright 2013 Robby Duke Code & Design
 *
 * Email: robby@noeff3x.com
 * Skype: rduke56
 */


package com.noeff3x.plugins.FxKDR;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class StatLogger implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDeath(EntityDeathEvent e) {
	if(e.getEntityType().equals(EntityType.PLAYER)) {
	    try {
		Player dead = (Player) e.getEntity();

		if(e.getEntity().getKiller() != null) {
		    String isStaff = "";
		    if(dead.hasPermission("fxkdr.staff")) {
			isStaff = ", `staff_Kills`=`staff_Kills` + 1";
		    }
		    PreparedStatement updateKiller = Config.sql.prepareStatement("update `players` set `kills`=`kills` + 1" + isStaff + " where `player`=?");

		    updateKiller.setString(1, dead.getKiller().getName());

		    int affected = updateKiller.executeUpdate();
		    updateKiller.close();

		    if(affected == 0) {
			PreparedStatement insertKiller = Config.sql.prepareStatement("insert into `players` ( `player`, `kills`) values (?, '1')");

			insertKiller.setString(1, dead.getKiller().getName());

			insertKiller.execute();
			insertKiller.close();
		    }
		}

		PreparedStatement updateDeaths = Config.sql.prepareStatement("update `players` set `deaths`=`deaths` + 1 where `player`=?");

		updateDeaths.setString(1, dead.getName());

		int affected01 = updateDeaths.executeUpdate();
		updateDeaths.close();

		if(affected01 == 0) {
		    PreparedStatement insertDeath = Config.sql.prepareStatement("insert into `players` ( `player`, `deaths`) values (?, '1')");

		    insertDeath.setString(1, dead.getName());

		    insertDeath.execute();
		    insertDeath.close();
		}
	    } catch (SQLException e1) {
		System.out.println(e1.getMessage());
	    }
	}
    }
}
