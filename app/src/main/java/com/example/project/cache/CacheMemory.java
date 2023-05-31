package com.example.project.cache;
import android.util.LruCache;

public class CacheMemory {
    private static LruCache<String, String> memoryCache;
    private static CacheMemory cacheMemory;
    private CacheMemory(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        memoryCache = new LruCache<String, String>(cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                return value.getBytes().length / 1024;
            }
        };
    }

    public static CacheMemory getMemoryCache() {
        if(cacheMemory ==null){
            cacheMemory =  new CacheMemory();
           return cacheMemory;
        }
        return cacheMemory;
    }

    public void putDataToCache(String key, String value) {
        if (memoryCache != null) {
            memoryCache.put(key, value);
        }
    }

    public  String getDataFromCache(String key) {
        if (memoryCache != null) {
            return memoryCache.get(key);
        }
        return null;
    }

    public  void removeDataFromCache(String key) {
        if (memoryCache != null) {
            memoryCache.remove(key);
        }
    }
}