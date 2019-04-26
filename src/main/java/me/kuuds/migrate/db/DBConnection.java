// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author KuuDS
 */
public class DBConnection {

    private String dbPath;
    private boolean connected = false;
    private Connection conn = null;

    public DBConnection(String dbPath) {
        this.dbPath = dbPath;
    }

    public void connect() {
        if (connected) {
            return;
        }
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            connected = true;
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            connected = false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean isConnected() {
        return connected;
    }

}
