package servisDB.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.project.Project;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long customerId;

    private String name;

    private int edrpou;

    private String product;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "customers")
    private Set<Project> project;

}
