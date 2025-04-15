package poppop.popopop.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;
import poppop.popopop.exception.RateLimitExceededException;
import poppop.popopop.repository.ClickRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;
    private final AsyncClickService asyncClickService;
    private final RateLimitService rateLimitService;

    public Click getClickCount(Grade grade, Ban ban) {
        return clickRepository.findByGradeAndBan(grade, ban).orElse(null);
    }

    public List<Click> getAllClickCounts() {
        return clickRepository.findAll();
    }

    public void incrementClick(Grade grade, Ban ban, HttpServletRequest request) {
        String clientIp = rateLimitService.getClientIp(request);

        if (!rateLimitService.tryConsume(clientIp)) {
            throw new RateLimitExceededException("Too many requests from this IP. Please try again later.");
        }

        asyncClickService.incrementClickAsync(grade, ban);
    }
}
