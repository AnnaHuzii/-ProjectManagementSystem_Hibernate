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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetInformationAboutAllCustomers implements Command {
    CustomerService customerDaoService = CustomerDaoService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");

        List<Customer> getAllNames = customerDaoService.getCustomerAll();
        ArrayList list = new ArrayList();
        Collections.addAll(list, getAllNames);
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("list", list)
        );
        engine.process("customers_all", simpleContext, resp.getWriter());

        resp.getWriter().close();
    }
}
