// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.handle;

import me.kuuds.migrate.AppConfig;
import me.kuuds.migrate.TypechoToPelican;
import me.kuuds.migrate.pojo.TypechoMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kuuds
 * @since 1.0
 */
public class MetaHandler {

    private final Connection conn;
    private final AppConfig config;
    private final String TABLE_NAME;

    public MetaHandler(Connection conn) {
        this.conn = conn;
        config = TypechoToPelican.config();
        TABLE_NAME = config.get("db.table.prefix") + "metas";
    }

    public Map<Integer, TypechoMeta> invoke() {
        Map<Integer, TypechoMeta> metas = new HashMap<>();
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Integer mid = rs.getInt("mid");
                String name = rs.getString("name");
                String type = rs.getString("type");
                TypechoMeta meta = new TypechoMeta(mid, name, type);
                metas.put(mid, meta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metas;
    }
}
