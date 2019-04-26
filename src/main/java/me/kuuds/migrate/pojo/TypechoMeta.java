// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.pojo;

/**
 * @author kuuds
 * @since 1.0
 */
public class TypechoMeta {

    public TypechoMeta(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    private Integer id;
    private String name;
    private String type;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
