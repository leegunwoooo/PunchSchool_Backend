package poppop.popopop.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poppop.popopop.entity.BlockedIp;
import poppop.popopop.repository.BlockedIpRepository;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final BlockedIpRepository blockedIpRepository;
    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null ? ip : "Unknown IP";
    }

    private Bucket createNewBucket(String ip) {
        Bandwidth limit = Bandwidth
                .builder()
                .capacity(20)
                .refillGreedy(20, Duration.ofSeconds(1))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket resolveBucket(String ip) {
        return ipBuckets.computeIfAbsent(ip, this::createNewBucket);
    }

    public boolean tryConsume(String ip) {
        if (isBlocked(ip)) {
            return false;
        }
        Bucket bucket = resolveBucket(ip);
        boolean allowed = bucket.tryConsume(1);
        if (!allowed) {
            blockIp(ip);
        }
        return allowed;
    }

    public boolean isBlocked(String ip) {
        return blockedIpRepository.existsByIp(ip);
    }

    private void blockIp(String ip) {
        BlockedIp blockedIp = new BlockedIp(ip);
        blockedIpRepository.save(blockedIp);
    }
}
