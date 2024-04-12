package app.domain;

import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = Date.valueOf(LocalDate.now());

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleProgress> moduleProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StepProgress> stepProgresses = new ArrayList<>();

    public Person(Long id) {
        this.id = id;
    }

    public void addModuleProgress(ModuleProgress progress) {
        moduleProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeModuleProgress(ModuleProgress progress) {
        moduleProgresses.remove(progress);
        progress.setPerson(null);
    }

    public void addLessonProgress(LessonProgress progress) {
        lessonProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeLessonProgress(LessonProgress progress) {
        lessonProgresses.remove(progress);
        progress.setPerson(null);
    }

    public void addStepProgress(StepProgress progress) {
        stepProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeStepProgress(StepProgress progress) {
        stepProgresses.remove(progress);
        progress.setPerson(null);
    }
}
