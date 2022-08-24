package servisDB.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import servisDB.company.Company;
import servisDB.customer.Customer;
import servisDB.skill.Skills;

import java.sql.Date;
import java.util.Set;

@Table(name = "project")
@Getter
@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_date")
    private Date startDate;

    private String name;

    private String description;

    private String cost;

    @ManyToOne
    @JoinColumn(name = "company_id")
    Company companies;

    @ManyToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customer_id")
    Set <Customer> customers;
    public Project() {}
}