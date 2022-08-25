package servisDB.developer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import servisDB.company.Company;
import servisDB.project.Project;
import servisDB.skill.Skill;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "developer")
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

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @JoinTable(name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Skill.class)
    private Set<Skill> skills = new HashSet<>();


    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Project.class)
    private Set<Project> projects = new HashSet<>();
}