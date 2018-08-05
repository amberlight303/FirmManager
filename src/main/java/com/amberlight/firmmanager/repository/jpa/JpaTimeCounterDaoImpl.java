package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.TimeCounterDao;
import com.amberlight.firmmanager.model.TimeCounter;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Jpa implementation of {@link TimeCounterDao}
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Repository("timeCounter")
public class JpaTimeCounterDaoImpl implements TimeCounterDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public TimeCounter getTimeCounterById(int counterId) {

       Query query = this.em.createQuery("SELECT timeCounter FROM TimeCounter timeCounter WHERE timeCounter.id = :counterId");
        query.setParameter("counterId", counterId);
        return (TimeCounter) query.getSingleResult();
    }

    @Override
    public void updateDaysFromStart(long daysFromStart, int counterId) {
        Query query =  this.em.createNativeQuery("UPDATE time_counter SET days_from_start = ? WHERE id = ?");
        query.setParameter(1, daysFromStart);
        query.setParameter(2, counterId);
        query.executeUpdate();
    }

    @Override
    public String toString() {
        return "JpaTimeCounterDaoImpl{" +
                "em=" + em +
                '}';
    }
}
