package servisDB.skill;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.customer.Customer;
import servisDB.developer.Developer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "skills")
public class Skills {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    @Column(name = "skill-level")
    @Enumerated(EnumType.STRING)
    private Level skillLevel;

    @ManyToMany(targetEntity = Developer.class, mappedBy = "skills")
    private Developer developers;

}
