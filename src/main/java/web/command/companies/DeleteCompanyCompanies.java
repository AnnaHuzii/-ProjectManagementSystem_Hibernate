package web.command.companies;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.company.CompanyDaoService;
import servisDB.company.CompanyService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DeleteCompanyCompanies implements Command {
    CompanyService companyConnections = CompanyDaoService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");
        Context context = new Context();
        String id = req.getParameter("companyID");
        boolean result = companyConnections.deleteByIdCompany(Long.parseLong(id));
        if (result) {
            context.setVariable("companyID", id);
            context.setVariable("trueDelete", result);
        } else {
            context.setVariable("companyID", id);
            context.setVariable("falseDelete", result);
        }



        engine.process("companies_delete", context, resp.getWriter());
        resp.getWriter().close();
    }
}
