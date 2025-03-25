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

    @GetMapping("/{grade}/{ban}")
    public Click getClickCount(@PathVariable Grade grade, @PathVariable Ban ban) {
        return clickService.getClickCount(grade, ban);
    }

    @GetMapping("/all")
    public List<Click> getAllClickCount(){
        return clickService.getAllClickCounts();
    }

    @PostMapping("/{grade}/{ban}")
    public void incrementClick(@PathVariable Grade grade,
                                @PathVariable Ban ban) {
        clickService.incrementClick(grade, ban);
    }
}
