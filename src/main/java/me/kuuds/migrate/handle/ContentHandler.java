// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.handle;

import me.kuuds.migrate.AppConfig;
import me.kuuds.migrate.TypechoToPelican;
import me.kuuds.migrate.pojo.TypechoContent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author kuuds
 * @since 1.0
 */
public class ContentHandler {

    private final Connection conn;
    private final AppConfig config;
    private final String TABLE_NAME;

    public ContentHandler(Connection conn) {
        this.conn = conn;
        config = TypechoToPelican.config();
        TABLE_NAME = config.get("db.table.prefix") + "contents";
    }

    public Map<Integer, TypechoContent> invoke(){
        Map<Integer, TypechoContent> contents = new HashMap<>();
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE type NOT LIKE 'page' ", TABLE_NAME);
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Integer cid = rs.getInt("cid");
                String title = rs.getString("title");
                String slug = rs.getString("slug");
                Date created = new Date(rs.getLong("created") * 1000);
                Date modified = new Date(rs.getLong("modified") * 1000);
                String text = rs.getString("text");
                String status = rs.getString("status");
                String type = rs.getString("type");
                TypechoContent content = new TypechoContent(cid, title, slug, created, modified, text, type, status);
                contents.put(cid, content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contents;
    }

}
