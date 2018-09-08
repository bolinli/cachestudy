package org.cachestudy.store;

import org.cachestudy.store.impl.BasicDataStore;

public interface DataStore<K, V> {
    ValueHolder<V> get(K key) throws StoreAccessException;

    BasicDataStore.PutStatus put(K key, V value) throws StoreAccessException;

    ValueHolder<V> remove(K key) throws StoreAccessException;

    void clear() throws StoreAccessException;

    enum PutStatus {
        //put new value
        PUT,
        //replace old  value
        UPDATE,
        //
        NOOP
    }
}
