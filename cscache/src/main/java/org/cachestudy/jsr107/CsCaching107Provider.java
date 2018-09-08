package org.cachestudy.jsr107;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置 管理 获取 创建多个CacheManager
 */
public class CsCaching107Provider implements CachingProvider {
    private static final String DEFAULT_URI_STRING = "urn:X-cscache:jsr107-default-config";
    private static final URI URI_DEFAULT = null;

    private final Map<ClassLoader, ConcurrentHashMap<URI, CacheManager>> cacheManagers
            = new WeakHashMap<>();

    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        uri = uri == null ? getDefaultURI() : uri;
        classLoader = classLoader == null ? getDefaultClassLoader() : classLoader;
        properties = properties == null ? new Properties() : cloneProperties(properties);
        ConcurrentHashMap<URI, CacheManager> cacheManagerByURI = cacheManagers.get(classLoader);
        if (cacheManagerByURI == null) {
            cacheManagerByURI = new ConcurrentHashMap<>();
        }
        CacheManager cacheManager = cacheManagerByURI.get(uri);
        if (cacheManager == null) {
            cacheManager = new CsCaching107Manager(this, classLoader, uri, properties);
            cacheManagerByURI.put(uri, cacheManager);
        }
        if (!cacheManagers.containsKey(classLoader)) {
            cacheManagers.put(classLoader, cacheManagerByURI);
        }
        return cacheManager;
    }

    private Properties cloneProperties(Properties properties) {
        Properties clong = new Properties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            clong.put(entry.getKey(), entry.getValue());
        }
        return clong;
    }

    @Override
    public ClassLoader getDefaultClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public URI getDefaultURI() {
        return URI_DEFAULT;
    }

    @Override
    public Properties getDefaultProperties() {
        return null;
    }

    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        return getCacheManager(uri, classLoader, getDefaultProperties());
    }

    @Override
    public CacheManager getCacheManager() {
        return getCacheManager(getDefaultURI(), getDefaultClassLoader(), null);
    }

    @Override
    public void close() {

    }

    @Override
    public void close(ClassLoader classLoader) {

    }

    @Override
    public void close(URI uri, ClassLoader classLoader) {

    }

    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }
}
