package servisDB.customer;
import servisDB.customer.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getById(long id);
    List<Customer> getCustomerAll();
    String update(Customer customer);
    String deleteById(long id);
}