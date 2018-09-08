package org.cachestudy;


import org.cachestudy.store.DataStore;
import org.cachestudy.store.StoreAccessException;
import org.cachestudy.store.ValueHolder;
import org.cachestudy.store.impl.BasicDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsCache<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsCache.class);

    private final DataStore<K, V> store;

    public CsCache(final BasicDataStore<K, V> store) {
        this.store = store;
    }

    public V get(final K key) {
        try {
            ValueHolder<V> vValueHolder = store.get(key);
            if (vValueHolder == null) {
                return null;
            }
            return vValueHolder.value();
        } catch (StoreAccessException e) {
            e.fillInStackTrace();
            return null;
        }

    }

    public void put(final K key, final V value) {
        try {
            store.put(key, value);
        } catch (StoreAccessException e) {
            e.printStackTrace();
        }
    }

    public V remove(K key) {
        try {
            ValueHolder<V> remove = store.remove(key);
            return remove.value();
        } catch (StoreAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        try {
            store.clear();
        } catch (StoreAccessException e) {
            e.printStackTrace();
        }
    }
}
