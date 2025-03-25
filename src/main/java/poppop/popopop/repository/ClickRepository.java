package poppop.popopop.repository;

import poppop.popopop.entity.Click;
import org.springframework.stereotype.Repository;

import poppop.popopop.entity.Ban;
import poppop.popopop.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {
    Click findByGradeAndBan(Grade grade, Ban ban);
}
