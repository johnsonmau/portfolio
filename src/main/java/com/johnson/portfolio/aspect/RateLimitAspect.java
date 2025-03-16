package com.johnson.portfolio.aspect;

import com.johnson.portfolio.exception.RateLimitExceededException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    private final Map<String, RateLimitInfo> ipRequestMap = new ConcurrentHashMap<>();
    private static final long LIMIT_PERIOD = 120 * 1000; // 2 minutes
    private static final int REQUEST_LIMIT = 3;

    @Before("@annotation(com.johnson.portfolio.annotation.RateLimit)")
    public void rateLimit() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        if (request == null) {
            logger.error("No current HTTP request found.");
            throw new IllegalStateException("No current HTTP request found.");
        }

        String clientIp = request.getHeader("X-Forwarded-For");
        logger.info("Rate limit check for IP: {}", clientIp);

        RateLimitInfo rateLimitInfo = ipRequestMap.computeIfAbsent(clientIp, ip -> new RateLimitInfo());

        synchronized (rateLimitInfo) {
            if (rateLimitInfo.isRateLimited()) {
                logger.warn("Rate limit exceeded for IP: {}", clientIp);
                throw new RateLimitExceededException("Too Many Requests - Rate limit exceeded");
            }
            rateLimitInfo.recordRequest();
            logger.info("Request recorded for IP: {} (count: {})", clientIp, rateLimitInfo.requestCount);
        }
    }

    private static class RateLimitInfo {
        private int requestCount = 0;
        private long startTime = Instant.now().toEpochMilli();

        boolean isRateLimited() {
            long currentTime = Instant.now().toEpochMilli();
            if (currentTime - startTime > LIMIT_PERIOD) {
                requestCount = 0;
                startTime = currentTime;
            }
            return requestCount >= REQUEST_LIMIT;
        }

        void recordRequest() {
            requestCount++;
        }
    }
}
