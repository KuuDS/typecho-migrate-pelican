// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.handle;

import me.kuuds.migrate.AppConfig;
import me.kuuds.migrate.TypechoToPelican;
import me.kuuds.migrate.pojo.TypechoRelation;

import java.sql.*;
import java.util.*;

/**
 * @author kuuds
 * @since 1.0
 */
public class RelationHandler {

    private final Connection conn;
    private final AppConfig config;
    private final String TABLE_NAME;

    public RelationHandler(Connection conn) {
        this.conn = conn;
        config = TypechoToPelican.config();
        TABLE_NAME = config.get("db.table.prefix") + "relationships";
    }

    public List<TypechoRelation> invoke(){
        List<TypechoRelation> typechoRelations = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                int cid = rs.getInt("cid");
                int mid = rs.getInt("mid");
                TypechoRelation typechoRelation = new TypechoRelation(cid, mid);
                typechoRelations.add(typechoRelation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typechoRelations;
    }

}
