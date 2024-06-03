package app.domain.progress;

import app.domain.Person;
import app.domain.Status;

import jakarta.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Progress {

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @ToString.Exclude
    private Person person;

//    @MapsId("statusId")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "status_id", nullable = false)
//    @ToString.Exclude
//    private Status status;
}
