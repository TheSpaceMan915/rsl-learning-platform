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
public class PersonStepProgress {

    @EmbeddedId
    private PersonStepProgressId id;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;

    @MapsId("stepId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    @ToString.Exclude
    private Step step;

    @MapsId("statusId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    private Status status;

    public PersonStepProgress(Person person, Step step, Status status) {
        this.id = new PersonStepProgressId(person.getId(), step.getId(), status.getId());
        this.person = person;
        this.step = step;
        this.status = status;
    }
}