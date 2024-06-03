package app.domain.progress;

import app.domain.Module;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.ids.ModuleProgressId;

import jakarta.persistence.*;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person_module_progress")
public class ModuleProgress extends Progress {

    @EmbeddedId
    private ModuleProgressId id;

    @MapsId("moduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @ToString.Exclude
    private Module module;

    public ModuleProgress(Person person, Module module) {
        super(person);
        this.id = new ModuleProgressId(person.getId(), module.getId());
        this.module = module;
    }
}