package servisDB.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import servisDB.company.Company;
import servisDB.customer.Customer;
import servisDB.developer.Developer;
import servisDB.skill.Skill;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_date")
    private Date startDate;

    private String name;

    private String description;

    private String cost;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable (name = "customer_id")
    private Set <Customer> customers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "projects")
    private Set<Developer> developers = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    @Fetch(FetchMode.JOIN)
    private Customer customer;


    public Project() {
    }

}