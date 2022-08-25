package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.Developer;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import servisDB.project.Project;
import servisDB.skill.Industry;
import servisDB.skill.Level;
import servisDB.skill.Skill;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UpdateDeveloperCommand implements Command {
    DeveloperService developerDaoService = DeveloperDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {
        resp.setContentType("text/html; charset=utf-8");
        Context context = new Context();
        long id = Long.parseLong(req.getParameter("developerFullName"));

        if (id == 0) {
            String email = req.getParameter("developerEmail");
            String skype = req.getParameter("developerSkype");
            String projectId = req.getParameter("developerProject");
            float salary = Float.parseFloat(req.getParameter("developerSalary"));
            String industry = req.getParameter("developerLanguage");
            Industry industryName = null;
            if (industry.equals(Industry.C_PLUS_PLUS.getIndustryName())) {
                industryName = Industry.C_PLUS_PLUS;
            } else if (industry.equals(Industry.C_SHARP.getIndustryName())) {
                industryName = Industry.C_SHARP;
            } else if (industry.equals(Industry.JS.getIndustryName())) {
                industryName = Industry.JS;
            } else if (industry.equals(Industry.JAVA.getIndustryName())) {
                industryName = Industry.JAVA;
            }
            String languageLevel = req.getParameter("developerLanguageLevel");
            Level levelName = null;
            if (languageLevel.equals(Level.JUNIOR.getLevelName())) {
                levelName = Level.JUNIOR;
            } else if (languageLevel.equals(Level.MIDDLE.getLevelName())) {
                levelName = Level.MIDDLE;
            } else if (languageLevel.equals(Level.SENIOR.getLevelName())) {
                levelName = Level.SENIOR;
            }
            Project project = new Project();
            project.setId(id);
            Set <Project> projectSet = new HashSet<>();
            projectSet.add(project);
            Skill skill = new Skill();
            skill.setIndustry(industryName);
            skill.setSkillLevel(levelName);
            Set<Skill> skillSet = new HashSet<>();
            skillSet.add(skill);

            Developer developer = new Developer();
            developer.setDeveloperId(id);
            developer.setEmail(email);
            developer.setSkype(skype);
            developer.setSalary(salary);
            developer.setProjects(projectSet);
            developer.setSkills(skillSet);

            boolean editDeveloper = developerDaoService.updateDeveloper(developer);

            context.setVariable("addDeveloper", editDeveloper);
            context.setVariable("id", id);
            context.setVariable("project", project);
            context.setVariable("salary", salary);
            context.setVariable("industryName", industryName);
            context.setVariable("levelName", levelName);

            engine.process("developer_update", context, resp.getWriter());

            resp.getWriter().close();
        } else {
            engine.process("error_developer_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
    }
}

