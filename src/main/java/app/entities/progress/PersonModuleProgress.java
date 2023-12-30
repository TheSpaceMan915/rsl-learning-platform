package app.entities.progress;

import app.entities.platform.Module;
import app.entities.platform.Person;
import app.entities.platform.Status;
import app.entities.progress.ids.PersonModuleProgressId;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_module_progress")
public class PersonModuleProgress extends Progress {

    @EmbeddedId
    private PersonModuleProgressId id;

    @MapsId("moduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @ToString.Exclude
    private Module module;

    public PersonModuleProgress(Person person, Module module, Status status) {
        super(person, status);
        this.id = new PersonModuleProgressId(person.getId(), module.getId(), status.getId());
        this.module = module;
    }
}