package poppop.popopop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clicks")
public class Click {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    private Grade grade;          // 학년 (enum)

    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    private Ban ban;              // 반 (enum)

    private int clickCount;

    public Click(Grade grade, Ban ban) {
        this.grade = grade;
        this.ban = ban;
        this.clickCount = 0;
    }

    public void increment() {
        this.clickCount++;
    }
}

