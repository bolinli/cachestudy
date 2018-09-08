package org.cachestudy.jsr107;

import org.cachestudy.CsCache;
import org.cachestudy.store.DataStore;
import org.cachestudy.store.impl.BasicDataStore;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CsCache107<K,V> implements Cache<K,V> {
    private CsCache<K,V> csCache = new CsCache<K,V>(new BasicDataStore<K,V>());
    private String cacheName;
    private CsCaching107Manager caching107Manager;
    private Configuration<K,V> configuration;
    private volatile boolean isClosed;

    public CsCache107(String cacheName, CsCaching107Manager caching107Manager, Configuration<K, V> configuration) {
        this.cacheName = cacheName;
        this.caching107Manager = caching107Manager;
        this.configuration = configuration;
        isClosed = false;
    }

    @Override
    public V get(K o) {
        return csCache.get(o);
    }

    @Override
    public Map getAll(Set set) {
        return null;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public void loadAll(Set set, boolean b, CompletionListener completionListener) {

    }

    @Override
    public void put(K k, V v) {
        csCache.put(k, v);
    }

    @Override
    public Object getAndPut(Object o, Object o2) {
        return null;
    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public boolean putIfAbsent(Object o, Object o2) {
        return false;
    }

    @Override
    public boolean remove(K k) {
        csCache.remove(k);
        return true;
    }

    @Override
    public boolean remove(Object o, Object o2) {
        return false;
    }

    @Override
    public Object getAndRemove(Object o) {
        return null;
    }

    @Override
    public boolean replace(Object o, Object o2, Object v1) {
        return false;
    }

    @Override
    public boolean replace(Object o, Object o2) {
        return false;
    }

    @Override
    public Object getAndReplace(Object o, Object o2) {
        return null;
    }

    @Override
    public void removeAll(Set set) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public void clear() {
        csCache.clear();
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public CacheManager getCacheManager() {
        return caching107Manager;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {

    }

    @Override
    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {

    }

    @Override
    public Iterator<Entry<K,V>> iterator() {
        return null;
    }

    @Override
    public Object unwrap(Class aClass) {
        return null;
    }

    @Override
    public Map invokeAll(Set set, EntryProcessor entryProcessor, Object... objects) {
        return null;
    }

    @Override
    public Object invoke(Object o, EntryProcessor entryProcessor, Object... objects) throws EntryProcessorException {
        return null;
    }

    @Override
    public Configuration getConfiguration(Class aClass) {
        return null;
    }
}
