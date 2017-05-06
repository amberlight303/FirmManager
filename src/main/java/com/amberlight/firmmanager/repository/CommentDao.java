package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.Comment;
import org.springframework.dao.DataAccessException;

/**
 * Repository interface for objects of {@link Comment} class.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public interface CommentDao {

    /**
     * Save a <code>Comment</code> to the data store, either inserting or updating it.
     * @param comment the <code>Comment</code> to save
     * @throws DataAccessException
     */
    void saveComment(Comment comment) throws DataAccessException;

    /**
     * Delete all <code>Comment</code>s from the data store
     * by <code>User</code>'s id, that have employee as author
     * @param userId
     * @throws DataAccessException
     */
    void deleteCommentsByUserId(int userId) throws DataAccessException;
}
