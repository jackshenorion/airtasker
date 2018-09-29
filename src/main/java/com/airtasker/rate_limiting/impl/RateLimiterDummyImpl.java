package com.airtasker.rate_limiting.impl;

import com.airtasker.rate_limiting.RateLimiter;

public class RateLimiterDummyImpl implements RateLimiter {
    @Override
    public long tillToOpenInSeconds(String requester) {
        return 0;
    }
}
