package org.openpnp;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Translations {
    private static final String BUNDLE_NAME = "org.openpnp.translations"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new UTF8Control());

    private Translations() {
    }

    public static String getString(String key) {
        try {
            String r = RESOURCE_BUNDLE.getString(key);
            if (System.getProperty("textUppercase") != null) {
                // Add the java command line -DtextUppercase=1 to enable this.
                // All text processed by the translation feature is converted to uppercase.
                // This can be used by developers to help spot any text in the user interface
                // which is incorrectly bypassing this translation mechanism.
                r = r.toUpperCase();
            }
            return r;
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static class UTF8Control extends ResourceBundle.Control {
        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}
