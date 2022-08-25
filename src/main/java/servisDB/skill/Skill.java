package servisDB.skill;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.developer.Developer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "skill")
public class Skill {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    @Column(name = "skill-level")
    @Enumerated(EnumType.STRING)
    private Level skillLevel;

    @ManyToMany(mappedBy = "skills")
    private Set<Developer> developers = new HashSet<>();


}
