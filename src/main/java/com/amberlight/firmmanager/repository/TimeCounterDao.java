package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.TimeCounter;
import org.springframework.dao.DataAccessException;

/**
 * Repository interface for objects of {@link TimeCounter} class.
 */
public interface TimeCounterDao {

    /**
     * Retrieve <code>TimeCounter</code> by its id from the data store.
     * @param counterId the id to search for
     * @return the <code>TimeCounter</code> if found
     * @throws DataAccessException
     */
    TimeCounter getTimeCounterById(int counterId) throws DataAccessException;

    /**
     * Update <code>daysFromStart</code> field of certain <code>Project</code> object
     * by its id.
     * @param daysFromStart the days from start time point to update
     * @param counterId the id to search for
     * @throws DataAccessException
     */
    void updateDaysFromStart(long daysFromStart, int counterId) throws DataAccessException;
}
