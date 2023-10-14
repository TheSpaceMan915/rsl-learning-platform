package app.entities.platform;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserModuleProgress> moduleProgresses = new ArrayList<>();

    public Status(String name) {
        this.name = name;
    }
}
