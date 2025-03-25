package poppop.popopop.controller;

import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;
import poppop.popopop.entity.dto.ClickCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import poppop.popopop.service.ClickService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClickController {

    private ClickService clickService;

    @GetMapping("/{grade}/{ban}")
    public Click getClickCount(@PathVariable Grade grade, @PathVariable Ban ban) {
        return clickService.getClickCount(grade, ban);
    }

    @GetMapping("/all")
    public List<Click> getAllClickCount(){
        return clickService.getAllClickCounts();
    }

    @PostMapping("/{grade}/{ban}")
    public Click incrementClick(@PathVariable Grade grade,
                                @PathVariable Ban ban,
                                @RequestBody ClickCountDTO clickCountDTO) {
        return clickService.saveClick(grade, ban, clickCountDTO.getClickCount());
    }
}
