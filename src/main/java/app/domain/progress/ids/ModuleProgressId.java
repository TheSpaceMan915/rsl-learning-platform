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
public class ModuleProgressId implements Serializable {

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuleProgressId that)) return false;
        return Objects.equals(getPersonId(), that.getPersonId()) && Objects.equals(getModuleId(), that.getModuleId()) && Objects.equals(getStatusId(), that.getStatusId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getModuleId(), getStatusId());
    }
}