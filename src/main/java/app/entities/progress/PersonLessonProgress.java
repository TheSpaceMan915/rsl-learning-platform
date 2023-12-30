package app.entities.progress;

import app.entities.platform.Person;
import app.entities.platform.Status;
import app.entities.progress.ids.PersonLessonProgressId;
import app.entities.platform.Lesson;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_lesson_progress")
public class PersonLessonProgress extends Progress {

    @EmbeddedId
    private PersonLessonProgressId id;

    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @ToString.Exclude
    private Lesson lesson;

    public PersonLessonProgress(Person person, Lesson lesson, Status status) {
        super(person, status);
        this.id = new PersonLessonProgressId(person.getId(), lesson.getId(), status.getId());
        this.lesson = lesson;
    }
}