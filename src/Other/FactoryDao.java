package Other;

import dao.*;
import inmemory.*;

public class FactoryDao {

    private static final String MYSQL = "mysql";
    private static final String MEMORY = "memory";
    private static final String JSON = "json";

    private FactoryDao() {}

    public static UserDao getUserDAO() {
        String daoType = Config.getPersistenceType();
        if (MYSQL.equalsIgnoreCase(daoType)) {
            return new UserDaoMYSQL();
        } else if (JSON.equalsIgnoreCase(daoType)) {
            return new UserDAOJSON();
        } else if (MEMORY.equalsIgnoreCase(daoType)) {
            return new UserDaoInMemory();
        }
        return null;
    }

    public static RichiestaSchedaNuotoDao getRichiestaSchedaNuotoDao() {
        String daoType = Config.getPersistenceType();
        if (MYSQL.equalsIgnoreCase(daoType)) {
            return new RichiestaSchedaNuotoDaoMYSQL();
        } else if (MEMORY.equalsIgnoreCase(daoType)) {
            return new RichiestaSchedaNuotoDaoInMemory();
        }
        return null;
    }

    public static SchedaNuotoDao getSchedaNuotoDao() {
        String daoType = Config.getPersistenceType();
        if (MYSQL.equalsIgnoreCase(daoType)) {
            return new SchedaNuotoDaoMYSQL();
        } else if (MEMORY.equalsIgnoreCase(daoType)) {
            return new SchedaNuotoDaoInMemory();
        }
        return null;
    }

    public static SchedaNuotoAssegnataDao getSchedaNuotoAssegnataDao() {
        return new SchedaNuotoAssegnataDaoInMemory();
    }
}
