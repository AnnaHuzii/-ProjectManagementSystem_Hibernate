package servisDB.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.project.Project;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long customerId;

    private String name;

    private int edrpou;

    private String product;

    @ManyToMany(mappedBy = "customers")
    private Set<Project> projects = new HashSet<>();

}
