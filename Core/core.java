/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Synapse.Database;
import Synapse.Session;
import Synapse.iDB;
import config.databaseConfig;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BlackMoon
 */
public class core {

    public static Boolean isRunning = false;

    public static void build() {
        Session.Database = databaseConfig.DATABASE;
        Session.Ip = databaseConfig.IP;
        Session.Password = databaseConfig.PASSWORD;
        Session.Port = databaseConfig.PORT;
        Session.User = databaseConfig.USER;
        Session.provider = databaseConfig.PROVIDER;
    }

    public static void startServer() {
        try {
            Database.getInstance().DB_INIT((iDB) Class.forName("Synapse.DB." + databaseConfig.PROVIDER.toUpperCase()).newInstance()).startConnection();
            isRunning = true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            isRunning = false;
        }
    }
}
