package servisDB.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long companyId;

    @Column(name = "name")
    private String companyName;

    @Column(name = "description")
    private String companyDescription;


}
