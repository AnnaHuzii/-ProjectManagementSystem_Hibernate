package web.command.customers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.customer.Customer;
import servisDB.customer.CustomerDaoService;
import servisDB.customer.CustomerService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CreateCustomersCommand implements Command {
    CustomerService companyDaoService = CustomerDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");

        Context context = new Context();
        String customerName = req.getParameter("customerName");
        String customerProduct = req.getParameter("customerProduct");
        int edrpou = 0;
        try {
            edrpou = Integer.parseInt(req.getParameter("edrpou"));
        }catch (Exception e){
            engine.process("error_customer_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setEdrpou(edrpou);
        customer.setProduct(customerProduct);

        if (!customerName.equals("") && edrpou != 0) {
            Customer getCreateCustomer = companyDaoService.createCustomer(customer);

            context.setVariable("customerId", getCreateCustomer.getCustomerId());
            context.setVariable("customerName", getCreateCustomer.getName());
            context.setVariable("edrpou", getCreateCustomer.getEdrpou());
            context.setVariable("customerProduct", getCreateCustomer.getProduct());
            resp.getWriter().close();
        } else {
            engine.process("error_customer_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
    }
}
