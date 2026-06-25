package Other;

import dao.*;
import InMemory.*;

public class FactoryDao {

    private FactoryDao() {}

    public static UserDao getUserDAO() {
        String daoType = Config.getPersistenceType();
        if ("mysql".equalsIgnoreCase(daoType)) {
            return new UserDaoMYSQL();
        } else if ("json".equalsIgnoreCase(daoType)) {
            return new UserDAOJSON();
        } else if ("memory".equalsIgnoreCase(daoType)) {
            return new UserDaoInMemory();
        }
        return null;
    }

    public static RichiestaSchedaNuotoDao getRichiestaSchedaNuotoDao() {
        String daoType = Config.getPersistenceType();
        if ("mysql".equalsIgnoreCase(daoType)) {
            return new RichiestaSchedaNuotoDaoMYSQL();
        } else if ("memory".equalsIgnoreCase(daoType)) {
            return new RichiestaSchedaNuotoDaoInMemory(); // se hai una versione in memoria
        }
        return null;
    }

    public static SchedaNuotoDao getSchedaNuotoDao() {
        String daoType = Config.getPersistenceType(); // mysql o memory
        if ("mysql".equalsIgnoreCase(daoType)) {
            return new SchedaNuotoDaoMYSQL();  // Se implementerai il DAO MySQL
        } else if ("memory".equalsIgnoreCase(daoType)) {
            return new SchedaNuotoDaoInMemory(); // DAO InMemory
        }
        return null;
    }

    public static SchedaNuotoAssegnataDao getSchedaNuotoAssegnataDao() {
        // Se vuoi InMemory:
        return new InMemory.SchedaNuotoAssegnataDaoInMemory();

        // Se vuoi MySQL:
        // return new Dao.SchedaNuotoAssegnataDaoMYSQL();
    }



}
