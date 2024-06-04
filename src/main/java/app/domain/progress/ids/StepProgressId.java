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
}