package app.entities.progress;

import app.entities.platform.Person;
import app.entities.platform.Status;
import app.entities.platform.Step;
import app.entities.progress.ids.PersonStepProgressId;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_step_progress")
public class PersonStepProgress extends Progress {

    @EmbeddedId
    private PersonStepProgressId id;

    @MapsId("stepId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    @ToString.Exclude
    private Step step;

    public PersonStepProgress(Person person, Step step, Status status) {
        super(person, status);
        this.id = new PersonStepProgressId(person.getId(), step.getId(), status.getId());
        this.step = step;
    }
}