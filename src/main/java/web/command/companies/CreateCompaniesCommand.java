package web.command.companies;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.company.Company;
import servisDB.company.CompanyDaoService;
import servisDB.company.CompanyService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CreateCompaniesCommand implements Command {
    CompanyService companyConnections = CompanyDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");
        Context context = new Context();

        String companyName = req.getParameter("companyName");
        String description = req.getParameter("companyDescription");

       if (!companyName.equals("")&& !description.equals("")){
           Company company = new Company();
           company.setCompanyName(companyName);
           company.setCompanyDescription(description);

           Company addNamesCompany = companyConnections.createCompany(company);;
            context.setVariable("companyID", addNamesCompany.getCompanyId());
            context.setVariable("companyName", addNamesCompany.getCompanyName());
            context.setVariable("companyDescription", addNamesCompany.getCompanyDescription());
            engine.process("companies_add", context, resp.getWriter());
            resp.getWriter().close();
        }else {
            engine.process("error_companies_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
    }
}
