// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.pojo;

/**
 * @author kuuds
 * @since 1.0
 */
public class TypechoRelation {

    public TypechoRelation(Integer cid, Integer mid) {
        this.cid = cid;
        this.mid = mid;
    }

    private Integer cid;
    private Integer mid;

    public Integer cid() {
        return cid;
    }

    public Integer mid() {
        return mid;
    }
}
