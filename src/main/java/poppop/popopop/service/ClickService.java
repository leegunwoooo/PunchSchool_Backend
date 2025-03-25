package poppop.popopop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;
import poppop.popopop.repository.ClickRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;

    private final AsyncClickService asyncClickService;

    public Click getClickCount(Grade grade, Ban ban) {
        return clickRepository.findByGradeAndBan(grade, ban).orElse(null);
    }

    public List<Click> getAllClickCounts() {
        return clickRepository.findAll();
    }

    public void incrementClick(Grade grade, Ban ban) {
        asyncClickService.incrementClickAsync(grade, ban);
    }
}
