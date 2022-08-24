package servisDB.developer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.project.Project;
import servisDB.skill.Skills;
import java.sql.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long developerId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String email;

    private String skype;

    private float salary;

    @JoinTable(name = "projects_developers",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Project> projects;

    @JoinTable(name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Skills> skills;

}