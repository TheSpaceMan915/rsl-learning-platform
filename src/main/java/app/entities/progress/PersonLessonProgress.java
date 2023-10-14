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
public class PersonLessonProgress {

    @EmbeddedId
    private PersonLessonProgressId id;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;

    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @ToString.Exclude
    private Lesson lesson;

    @MapsId("statusId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    private Status status;

    public PersonLessonProgress(Person person, Lesson lesson, Status status) {
        this.id = new PersonLessonProgressId(person.getId(), lesson.getId(), status.getId());
        this.person = person;
        this.lesson = lesson;
        this.status = status;
    }
}