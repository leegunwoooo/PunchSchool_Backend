package poppop.popopop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;

import org.springframework.web.bind.annotation.*;
import poppop.popopop.service.ClickService;
import poppop.popopop.service.RateLimitService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClickController {

    private final ClickService clickService;
    private final RateLimitService rateLimitService;


    @GetMapping("/{grade}/{ban}")
    public Click getClickCount(@PathVariable Grade grade, @PathVariable Ban ban) {
        return clickService.getClickCount(grade, ban);
    }

    @GetMapping("/all")
    public List<Click> getAllClickCount(){
        return clickService.getAllClickCounts();
    }

    @PostMapping("/{grade}/{ban}")
    public void incrementClick(@PathVariable Grade grade, @PathVariable Ban ban, HttpServletRequest request) {
        String clientIp = rateLimitService.getClientIp(request);
        if(rateLimitService.tryConsume(clientIp)){
            clickService.incrementClick(grade, ban, request);
        }
    }
}
