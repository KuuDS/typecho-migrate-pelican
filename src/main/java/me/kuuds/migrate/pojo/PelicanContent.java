// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate.pojo;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author kuuds
 * @since 1.0
 */
public class PelicanContent {

    private String title;
    private String slug;
    private String created;
    private String modified;
    private String content;
    private String status;
    private String author;
    private String category;
    private String tags;

    public PelicanContent(String title, String slug, String created, String modified, String content
            , String status, String author, String category, String tags) {
        this.title = title;
        this.slug = slug;
        this.created = created;
        this.modified = modified;
        this.content = content;
        this.status = status;
        this.author = author;
        this.category = category;
        this.tags = tags;
    }

    public void doc(String outputPath) {

        /**
         * Title: My super title
         * Date: 2010-12-03 10:20
         * Modified: 2010-12-05 19:30
         * Category: Python
         * Tags: pelican, publishing
         * Slug: my-super-post
         * Status: hidden
         * Authors: Alexis Metaireau, Conan Doyle
         */

        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append('\n')
                .append("Date: ").append(created).append('\n')
                .append("Modify: ").append(modified).append('\n');
        if (category.length() > 0) {
            sb.append("Category: ").append(category).append('\n');
        }
        if (tags.length() > 0) {
            sb.append("Tags: ").append(tags).append('\n');
        }
        sb.append("Slug: ").append(slug).append('\n')
                .append("Author: ").append(author).append('\n')
                .append("Status: ").append(status).append("\n\n")
                .append(content);

        String filename = outputPath + File.separator + slug + ".md";
        File file = new File(filename);

        try {
            if (!file.exists() && !file.createNewFile()) {
                System.err.println("can't create file: " + file.getAbsolutePath());
                return;
            }
            OutputStream out = new FileOutputStream(file, false);
            out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.err.println("can't find file: " + file.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
