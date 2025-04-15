package poppop.popopop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Click;
import poppop.popopop.entity.Grade;
import poppop.popopop.repository.ClickRepository;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncClickService {

    private final ClickRepository clickRepository;


    @Async
    @Transactional
    public void incrementClickAsync(Grade grade, Ban ban) {
        Click click = clickRepository.findByGradeAndBan(grade, ban)
                .orElseGet(() -> new Click(grade, ban));

        click.increment();
        CompletableFuture.completedFuture(clickRepository.save(click));
    }
}

