package app.domain;

import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleProgress> moduleProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StepProgress> stepProgresses = new ArrayList<>();

    public Person(String accessToken, String refreshToken, Timestamp createdAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
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
