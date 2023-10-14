package app.entities.progress.ids;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PersonLessonProgressId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "status_id")
    private Long statusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonLessonProgressId that)) return false;
        return Objects.equals(getPersonId(), that.getPersonId()) && Objects.equals(getLessonId(), that.getLessonId()) && Objects.equals(getStatusId(), that.getStatusId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getLessonId(), getStatusId());
    }
}