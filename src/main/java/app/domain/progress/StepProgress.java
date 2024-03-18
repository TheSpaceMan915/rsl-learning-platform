package app.domain.progress;

import app.domain.Person;
import app.domain.Status;
import app.domain.Step;
import app.domain.progress.ids.StepProgressId;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_step_progress")
public class StepProgress extends Progress {

    @EmbeddedId
    private StepProgressId id;

    @MapsId("stepId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    @ToString.Exclude
    private Step step;

    public StepProgress(Person person, Step step, Status status) {
        super(person, status);
        this.id = new StepProgressId(person.getId(), step.getId(), status.getId());
        this.step = step;
    }
}