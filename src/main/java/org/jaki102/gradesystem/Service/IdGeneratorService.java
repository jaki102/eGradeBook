package org.jaki102.gradesystem.Service;

import org.jaki102.gradesystem.dao.DaoManager;
import org.jaki102.gradesystem.dao.IdGeneratorDao;

public class IdGeneratorService {
    /**
     * Generating new unique student index
     *
     * @return new unique index value
     */
    public int generateStudentIndex() {
        DaoManager manager = new DaoManager();
        IdGeneratorDao idGeneratorDao = manager.getIdGeneratorDao();
        return idGeneratorDao.generateStudentIndex();
    }

    /**
     * Generating new unique course id
     *
     * @return new unique id value
     */
    public int generateCourseId() {
        DaoManager manager = new DaoManager();
        IdGeneratorDao idGeneratorDao = manager.getIdGeneratorDao();
        return idGeneratorDao.generateCourseId();
    }

    /**
     * Generatin new unique grade id
     *
     * @return new unique id value
     */
    public int generateGradeId() {
        DaoManager manager = new DaoManager();
        IdGeneratorDao idGeneratorDao = manager.getIdGeneratorDao();
        return idGeneratorDao.generateGradeId();
    }
}
