// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.pojo;

import java.util.Date;

/**
 * @author kuuds
 * @since 1.0
 */
public class TypechoContent {
    private Integer cid;
    private String slug;
    private String title;
    private Date created;
    private Date modified;
    private String type;
    private String content;
    private String status;

    public TypechoContent(Integer cid, String title, String slug, Date created, Date modified,
                          String content, String type, String status) {
        this.cid = cid;
        this.slug = slug;
        this.title = title;
        this.created = created;
        this.modified = modified;
        this.content = content;
        this.type = type;
        this.status = status;
    }

    public Integer getCid() {
        return cid;
    }

    public String getSlug() {
        return slug;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
