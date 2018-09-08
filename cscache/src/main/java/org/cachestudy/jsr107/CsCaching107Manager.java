package org.cachestudy.jsr107;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 创建 配置 管理 获取多个cache
 * */
public class CsCaching107Manager implements CacheManager {
    private final CsCaching107Provider cachingProvider;
    private final ClassLoader classLoader;
    private final URI uri;
    private final Properties props;
    private volatile boolean isClosed;
    private static Logger log = LoggerFactory.getLogger(CsCaching107Manager.class);

    public CsCaching107Manager(CsCaching107Provider cachingProvider, ClassLoader classLoader, URI uri, Properties props) {
        this.cachingProvider = cachingProvider;
        this.classLoader = classLoader;
        this.uri = uri;
        this.props = props;
    }

    /**
     * 存储缓存的实例 可进行并发的对caches的的读写
     * */
    private final ConcurrentMap<String,CsCache107<?,?>> caches
            = new ConcurrentHashMap<String, CsCache107<?,?>>();


    @Override
    public CachingProvider getCachingProvider() {
        return cachingProvider;
    }

    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    /**
     * 创建缓存
     * */
    @Override
    synchronized public <K,V,C extends Configuration<K,V>> Cache<K,V>  createCache(String cacheName, C configuration) {
        if(isClosed){
            throw new IllegalStateException();
        }
        checkNotNull(cacheName,"cacheName");
        checkNotNull(configuration,"configuration");
        CsCache107 cache = caches.get(cacheName);
        if(cache == null){
            synchronized (caches){
                cache = caches.get(cacheName);
                if(cache == null){
                    cache = new CsCache107<K,V>(cacheName,this,configuration);
                    caches.put(cache.getName(),cache);
                }
            }
            return (Cache<K,V>)cache;
        }else{
            throw new CacheException(cacheName + "already exists");
        }
    }

    /**
     * 获取缓存
     * */
    @Override
    synchronized public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyClasszz, Class<V> valueClasszz) {
        if(isClosed){
            throw new IllegalStateException();
        }
        checkNotNull(keyClasszz,"keyType");
        checkNotNull(valueClasszz,"valueType");
        //获取缓存的实例
        CsCache107<?,?> cache = caches.get(cacheName);
        if(cache == null){
            return null;
        }else{
            //判断key value的类型传入的是否和设定的类型一致
            Configuration configuration = cache.getConfiguration(Configuration.class);
            if(configuration.getKeyType()!=null && configuration.getKeyType().equals(keyClasszz)){
                return (Cache<K,V>)cache;
            }else{
                throw new ClassCastException("key require type " + configuration.getKeyType());
            }
        }
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) {
        return (Cache<K, V>) getCache(s,Object.class,Object.class);
    }

    @Override
    public Iterable<String> getCacheNames() {
        return null;
    }

    @Override
    public void destroyCache(String s) {

    }

    @Override
    public void enableManagement(String s, boolean b) {

    }

    @Override
    public void enableStatistics(String s, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    private void checkNotNull(Object object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " can not be null");
        }
    }
}
