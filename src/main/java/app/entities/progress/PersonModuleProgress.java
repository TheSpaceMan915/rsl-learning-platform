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
public class PersonModuleProgress {

    @EmbeddedId
    private PersonModuleProgressId id;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;

    @MapsId("moduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @ToString.Exclude
    private Module module;

    @MapsId("statusId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    private Status status;

    public PersonModuleProgress(Person person, Module module, Status status) {
        this.id = new PersonModuleProgressId(person.getId(), module.getId(), status.getId());
        this.person = person;
        this.module = module;
        this.status = status;
    }
}