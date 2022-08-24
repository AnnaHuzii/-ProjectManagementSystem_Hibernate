package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.Developer;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import servisDB.developer.Sex;
import servisDB.project.Project;
import servisDB.project.ProjectDaoService;
import servisDB.project.ProjectService;
import servisDB.skill.*;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CreateDeveloperCommand implements Command {
    DeveloperService developerConnections = DeveloperDaoService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {

        Context context = new Context();

        String fullName = req.getParameter("developerFullName");
        String email = req.getParameter("developerEmail");
        Date birthDate = null;
        try {
            birthDate = Date.valueOf(LocalDate.parse(req.getParameter("developerBirthDate")));
        }catch (Exception e){

            engine.process("error_developer_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
        if (!fullName.equals("") && !email.equals("")) {

            String sex = req.getParameter("developerSex");
            String skype = req.getParameter("developerSkype");
            long reqProject = Long.parseLong(req.getParameter("developerProject"));
            float salary = Float.parseFloat(req.getParameter("developerSalary"));
            String industry = req.getParameter("developerLanguage");
            String languageLevel = req.getParameter("developerLanguageLevel");

            Sex sexName = null;
            if (sex.equals(Sex.MALE.getSexName())) {
                sexName = Sex.MALE;
            } else if (sex.equals(Sex.FEMALE.getSexName())) {
                sexName = Sex.FEMALE;
            } else if (sex.equals(Sex.UNKNOWN.getSexName())) {
                sexName = Sex.UNKNOWN;
            }

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

            Level levelName = null;
            if (languageLevel.equals(Level.JUNIOR.getLevelName())) {
                levelName = Level.JUNIOR;
            } else if (languageLevel.equals(Level.MIDDLE.getLevelName())) {
                levelName = Level.MIDDLE;
            } else if (languageLevel.equals(Level.SENIOR.getLevelName())) {
                levelName = Level.SENIOR;
            }

            SkillsService skillsDaoService = SkillsDaoService.getInstance();
            Skills skills = new Skills();
            long skillsId = skillsDaoService.getSkillsById(industryName, levelName);
            skills.setSkillId(skillsId);

            ProjectService projectDaoService = ProjectDaoService.getInstance();
            Project projectId = projectDaoService.getProjectById(reqProject);

            Developer developer = new Developer();
            developer.setFullName(fullName);
            developer.setBirthDate(birthDate);
            developer.setEmail(email);
            developer.setSkype(skype);
            developer.setSex(sexName);
            developer.setSalary(salary);
            developer.setProjects(projectId);
            developer.setSkills(skills);
            Developer addDeveloper = developerConnections.createDeveloper(developer);

            context.setVariable("fullName", addDeveloper.getFullName());
            context.setVariable("birthDate", addDeveloper.getBirthDate());
            context.setVariable("project", addDeveloper.getProjects());
            context.setVariable("salary", addDeveloper.getSalary());
            context.setVariable("skills", addDeveloper.getSkills());

            resp.setContentType("text/html, charset=utf-8");
            engine.process("developer_add", context, resp.getWriter());
        } else {
            engine.process("error_developer_incorrectly", context, resp.getWriter());
        } resp.getWriter().close();
    }
}

