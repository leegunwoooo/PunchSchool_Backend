package poppop.popopop.controller;

import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;

import org.springframework.web.bind.annotation.*;
import poppop.popopop.service.ClickService;

import java.util.List;

@RestController
public class ClickController {

    private final ClickService clickService;

    public ClickController(ClickService clickService) {
        this.clickService = clickService;
    }

    @GetMapping
    public Click getClickCount(@RequestParam Grade grade, @RequestParam Ban ban) {
        return clickService.getClickCount(grade, ban);
    }

    @GetMapping("/all")
    public List<Click> getAllClickCount(){
        return clickService.getAllClickCounts();
    }

    @PostMapping
    public void incrementClick(@RequestParam Grade grade, @RequestParam Ban ban) {
        clickService.incrementClick(grade, ban);
    }
}
