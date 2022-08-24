package servisDB.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.project.Project;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long companyId;

    @Column(name = "name")
    private String companyName;

    @Column(name = "description")
    private String companyDescription;

    @OneToMany(mappedBy = "companies")
    Set<Project> project;
}
