package app.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @ToString.Exclude
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    public Lesson(Long id) {
        this.id = id;
    }

    public void addStep(Step step) {
        steps.add(step);
        step.setLesson(this);
    }

    public void removeStep(Step step) {
        steps.remove(step);
        step.setLesson(null);
    }
}