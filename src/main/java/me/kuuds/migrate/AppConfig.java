// typecho-migrate-pelican
// Copyright: 2019, KuuDS
// License: Mozilla Public License v2.0 (MPL v2.0)
package me.kuuds.migrate;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class AppConfig {
    private final Map<String, String> map;

    AppConfig() {
        map = new ConcurrentHashMap<>();
    }

    public AppConfig read(String filePath) {
        InputStream fin = this.getClass().getClassLoader().getResourceAsStream(filePath);;
        try {
            Properties properties = new Properties();
            properties.load(fin);
            properties.forEach((k, v) -> {
                if (k instanceof String && v instanceof String) {
                    map.put((String) k, (String) v);
                }
            });
        } catch (FileNotFoundException e) {
            System.err.println("Can't find config file.");
            TypechoToPelican.shutdown(ExitState.ERR_CONFIG);
        } catch (IOException e) {
            System.err.println("error while loading file.");
            TypechoToPelican.shutdown(ExitState.ERR_CONFIG);
        } finally {
            try {
                if (fin != null) {

                    fin.close();
                }
            } catch (IOException e) {
                System.err.println("error while close file.");
                TypechoToPelican.shutdown(ExitState.ERR_CONFIG);
            }
        }
        return this;
    }

    public String get(String key) {
        return map.get(key);
    }
}
