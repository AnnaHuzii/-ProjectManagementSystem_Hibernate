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

public class UpdateCompanyCompanies implements Command {
    CompanyService companyConnections = CompanyDaoService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");
        Context context = new Context();

        long companyID = Long.parseLong(req.getParameter("companyID"));
        String companyName = req.getParameter("companyName");
        String description = req.getParameter("companyDescription");

        if (companyID != 0 && !companyName.equals("")) {
            Company company = new Company();
            company.setCompanyId(companyID);
            company.setCompanyName(companyName);
            company.setCompanyDescription(description);
            boolean result = companyConnections.updateCompany(companyID, company);
            resp.setContentType("text/html; charset=utf-8");
            if (result) {
                context.setVariable("companyID", companyID);
                context.setVariable("companyName", companyName);
                context.setVariable("companyDescription", description);
                context.setVariable("trueUpdate", result);
                engine.process("companies_update", context, resp.getWriter());
            } else {
                context.setVariable("companyName", companyName);
                engine.process("error_update", context, resp.getWriter());
            }
        }else {
            engine.process("error_companies_incorrectly", context, resp.getWriter());
        }
        resp.getWriter().close();
    }
}
