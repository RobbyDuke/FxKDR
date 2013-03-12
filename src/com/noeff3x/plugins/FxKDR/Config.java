/**
 * @author Robby Duke
 * @date Mar 10, 2013
 * @file Config.java
 * @project FxKDR
 * 
 * Copyright 2013 Robby Duke Code & Design
 *
 * Email: robby@noeff3x.com
 * Skype: rduke56
 */


package com.noeff3x.plugins.FxKDR;

import java.sql.Connection;

import org.bukkit.Server;

public class Config {
    public static Connection sql;
    public static Server server;
    
    public static String SQL_Host;
    public static String SQL_User;
    public static String SQL_Pass;
    public static String SQL_Data;
    
    public static String message_Prefix;
    
}
