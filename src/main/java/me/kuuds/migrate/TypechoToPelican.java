// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate;

import me.kuuds.migrate.db.DBConnection;
import me.kuuds.migrate.handle.MetaHandler;
import me.kuuds.migrate.handle.ContentHandler;
import me.kuuds.migrate.handle.PelicanContentBuilder;
import me.kuuds.migrate.handle.RelationHandler;
import me.kuuds.migrate.pojo.TypechoContent;
import me.kuuds.migrate.pojo.TypechoMeta;
import me.kuuds.migrate.pojo.TypechoRelation;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author kuuds
 * @since 1.0
 */
public class TypechoToPelican {

    private static AppConfig config;

    public static void main(String[] args) {
        config = new AppConfig().read("config.properties");

        DBConnection dbConnection = new DBConnection(config.get("db.path"));
        dbConnection.connect();

        if(!dbConnection.isConnected()){
            shutdown(ExitState.ERR_SQL);
        }

        Map<Integer, TypechoMeta> typechoCategories;
        Map<Integer, TypechoContent> typechoContents;
        List<TypechoRelation> typechoRelations;

        Connection conn = dbConnection.getConnection();
        MetaHandler metaHandler = new MetaHandler(conn);
        ContentHandler contentHandler = new ContentHandler(conn);
        RelationHandler relationHandler = new RelationHandler(conn);

        typechoCategories = metaHandler.invoke();
        typechoContents = contentHandler.invoke();
        typechoRelations = relationHandler.invoke();

        PelicanContentBuilder.build(typechoContents, typechoCategories, typechoRelations);

        dbConnection.close();
        shutdown(ExitState.NORMAL);
    }

    public static AppConfig config(){
        return config;
    }

    public static void shutdown(ExitState state){
        System.out.println("EXIT! code [ " + state + "].");
        System.exit(state.ordinal());
    }

}
