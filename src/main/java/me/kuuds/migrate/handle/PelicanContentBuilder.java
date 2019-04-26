// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.handle;

import me.kuuds.migrate.TypechoToPelican;
import me.kuuds.migrate.pojo.PelicanContent;
import me.kuuds.migrate.pojo.TypechoContent;
import me.kuuds.migrate.pojo.TypechoMeta;
import me.kuuds.migrate.pojo.TypechoRelation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author kuuds
 * @since 1.0
 */
public class PelicanContentBuilder {

    private static final String META_SPLIT = ", ";

    public static void build(Map<Integer, TypechoContent> contentMap,
                             Map<Integer, TypechoMeta> metaMap,
                             List<TypechoRelation> relationList) {
        final String authorName = TypechoToPelican.config().get("author.name");
        final String outputPath = TypechoToPelican.config().get("output.path");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);

        contentMap.entrySet().parallelStream().map(es -> {
            StringBuilder categoryBuilder = new StringBuilder();
            StringBuilder tagBuilder = new StringBuilder();
            relationList.stream()
                    .filter(relation -> relation.cid().equals(es.getKey()))
                    .map(TypechoRelation::mid)
                    .map(metaMap::get)
                    .filter(Objects::nonNull)
                    .distinct()
                    .forEach(meta -> {
                        if ("category".equals(meta.getType())) {
                            categoryBuilder.append(meta.getName()).append(META_SPLIT);
                        } else if ("tag".equals(meta.getType())) {
                            tagBuilder.append(meta.getName()).append(META_SPLIT);
                        }
                    });
            int cateLength = categoryBuilder.length();
            int tagLength = tagBuilder.length();
            if (cateLength > META_SPLIT.length()) {
                categoryBuilder.delete(cateLength - 2, cateLength);
            }
            if (tagLength > META_SPLIT.length()) {
                tagBuilder.delete(tagLength - 2 , tagLength);
            }

            TypechoContent src = es.getValue();
            String status;
            if ("private".equals(src.getStatus())) {
                status = "hidden";
            } else if ("post".equals(src.getType())) {
                status = "published";
            } else if ("post_draft".equals(src.getType())) {
                status = "draft";
            } else {
                return null;
            }

            return new PelicanContent(
                    src.getTitle(),
                    src.getSlug(),
                    dateFormat.format(src.getCreated()),
                    dateFormat.format(src.getModified()),
                    src.getContent(),
                    status,
                    authorName,
                    categoryBuilder.toString(),
                    tagBuilder.toString()
            );
        }).filter(Objects::nonNull).forEach(content -> content.doc(outputPath));
    }
}