package servisDB.company;

import java.util.List;

public interface CompanyService {
    Company createCompany(Company company);
    Company getCompanyById(long id);
    List<Company> getCompanyAll();
    boolean updateCompany(long id, Company company);
    boolean deleteByIdCompany(long id);
}