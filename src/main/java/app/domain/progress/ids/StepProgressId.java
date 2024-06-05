package app.domain.progress.ids;

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
public class StepProgressId implements Serializable {

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "step_id", nullable = false)
    private Long stepId;

    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StepProgressId that)) return false;
        return Objects.equals(getPersonId(), that.getPersonId()) && Objects.equals(getStepId(), that.getStepId()) && Objects.equals(getStatusId(), that.getStatusId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getStepId(), getStatusId());
    }
}