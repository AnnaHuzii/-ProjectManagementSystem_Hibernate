package servisDB.customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerById(long id);
    List<Customer> getCustomerAll();
    boolean update(Customer customer);
    boolean deleteById(long id);
}