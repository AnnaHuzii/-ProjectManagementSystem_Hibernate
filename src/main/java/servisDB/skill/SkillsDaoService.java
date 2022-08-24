package servisDB.skill;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class SkillsDaoService implements SkillsService{

    private static final SkillsDaoService INSTANCE;

    static {
        INSTANCE = new SkillsDaoService();
    }

    public static SkillsDaoService getInstance() {
        return INSTANCE;
    }

    @Override
    public long getSkillsById(Industry industry, Level level) {

        try (Session session = openSession()) {
            NativeQuery<Long> querySkill = session.createNativeQuery(
                    "SELECT skills.id FROM skills WHERE industry = :paramIndustry AND " +
                            "skill_level = :paramSkillLevel",
                    Long.class
            );
            querySkill.setParameter("paramIndustry", industry.getIndustryName());
            querySkill.setParameter("paramSkillLevel", level.getLevelName());

            List<Long> list = querySkill.list();

            return list.get(0);
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
