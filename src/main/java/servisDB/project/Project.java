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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String cost;

    @Column(name = "company_id")
    private Company companyId;

    @Column(name = "customer_id")
    private Customer customerId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customer_id")
    private Customer customer;

}