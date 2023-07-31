package org.orders.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.orders.Main;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Hikari {
    private String username;
    private String password;
    private String host;
    private String dbname;
    private int port;
    private boolean ssl;

    private HikariDataSource dataSource;

    public Hikari() {
        this.host = Main.getInstance().getConfig().getString("database.host");
        this.dbname = Main.getInstance().getConfig().getString("database.dbName");
        this.port = Main.getInstance().getConfig().getInt("database.port");
        this.ssl = Main.getInstance().getConfig().getBoolean("database.ssl");

        this.username = Main.getInstance().getConfig().getString("database.username");
        this.password = Main.getInstance().getConfig().getString("database.password");
    }

    public void initialize() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.mariadb.jdbc.MariaDbDataSource");
        props.setProperty("dataSource.user", this.username);
        props.setProperty("dataSource.password", this.password);
        props.setProperty("dataSource.databaseName", this.dbname);
        props.put("dataSource.logWriter", new PrintWriter(System.out));
        props.setProperty("dataSource.serverName", host);
        props.setProperty("dataSource.portNumber", String.valueOf(port));
        props.setProperty("cachePrepStmts", "true");
        props.setProperty("prepStmtCacheSize", "250");
        props.setProperty("prepStmtCacheSqlLimit", "2048");
        props.setProperty("requireSSL", String.valueOf(ssl));

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + dbname);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setMaximumPoolSize(15);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initialize();
        }
        return dataSource.getConnection();
    }

    public final ArrayList<DBRow> query(final String query, final Object... variables) {
        final ArrayList<DBRow> rows = new ArrayList<>();

        ResultSet result = null;
        PreparedStatement pState = null;
        Connection connection = null;

        try {
            connection = this.getConnection();
            pState = connection.prepareStatement(query);
            for (int i = 1; i <= variables.length; ++i) {
                Object obj = variables[i - 1];
                if (obj != null && obj.toString().equalsIgnoreCase("null")) {
                    obj = null;
                }
                if (obj instanceof Blob) {
                    pState.setBlob(i, (Blob) obj);
                } else if (obj instanceof InputStream) {
                    pState.setBinaryStream(i, (InputStream) obj);
                } else if (obj instanceof byte[]) {
                    pState.setBytes(i, (byte[]) obj);
                } else if (obj instanceof Boolean) {
                    pState.setBoolean(i, (boolean) obj);
                } else if (obj instanceof Integer) {
                    pState.setInt(i, (int) obj);
                } else if (obj instanceof String) {
                    pState.setString(i, (String) obj);
                } else {
                    pState.setObject(i, obj);
                }
            }
            if (pState.execute()) {
                result = pState.getResultSet();
            }
            if (result != null) {
                final ResultSetMetaData mtd = result.getMetaData();
                final int columnCount = mtd.getColumnCount();
                while (result.next()) {
                    final DBRow row = new DBRow();
                    for (int l = 0; l < columnCount; ++l) {
                        final String columnName = mtd.getColumnName(l + 1);
                        row.addCell(columnName, result.getObject(columnName));
                    }
                    rows.add(row);
                }
            }
        } catch (Exception var23) {

            var23.printStackTrace();

        } finally {
            try {
                connection.close();
                pState.close();
                result.close();
            } catch (Exception ex) {
            }
        }
        return rows;
    }

    public void shutdown() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

}
