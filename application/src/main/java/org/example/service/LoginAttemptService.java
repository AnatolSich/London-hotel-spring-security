package org.example.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public LoadingCache<String, Integer> getAttemptsCache() {
        return attemptsCache;
    }

    public void loginSucceeded(final String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(final String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
        System.out.println("key: " + key);
        System.out.println("attempts: " + attempts);
    }

    public boolean isBlocked(final String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }
}
