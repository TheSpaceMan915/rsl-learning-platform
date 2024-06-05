package app.domain.progress;

import app.domain.Person;
import app.domain.Status;
import app.domain.progress.ids.LessonProgressId;
import app.domain.Lesson;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_lesson_progress")
public class LessonProgress extends Progress {

    @EmbeddedId
    private LessonProgressId id;

    @MapsId("lessonId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @ToString.Exclude
    private Lesson lesson;

    public LessonProgress(Person person, Lesson lesson) {
        super(person);
        this.id = new LessonProgressId(person.getId(), lesson.getId());
        this.lesson = lesson;
    }
}