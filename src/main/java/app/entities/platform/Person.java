package app.entities.platform;

import app.entities.progress.PersonLessonProgress;
import app.entities.progress.PersonModuleProgress;
import app.entities.progress.PersonStepProgress;

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
    private List<PersonModuleProgress> moduleProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonLessonProgress> lessonProgresses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonStepProgress> stepProgresses = new ArrayList<>();

    public Person(String accessToken, String refreshToken, Timestamp createdAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
    }

    public void addModuleProgress(PersonModuleProgress progress) {
        moduleProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeModuleProgress(PersonModuleProgress progress) {
        moduleProgresses.remove(progress);
        progress.setPerson(null);
    }

    public void addLessonProgress(PersonLessonProgress progress) {
        lessonProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeLessonProgress(PersonLessonProgress progress) {
        lessonProgresses.remove(progress);
        progress.setPerson(null);
    }

    public void addStepProgress(PersonStepProgress progress) {
        stepProgresses.add(progress);
        progress.setPerson(this);
    }

    public void removeStepProgress(PersonStepProgress progress) {
        stepProgresses.remove(progress);
        progress.setPerson(null);
    }
}
