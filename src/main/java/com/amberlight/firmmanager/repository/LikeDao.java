package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.Like;
import org.springframework.dao.DataAccessException;

/**
 */
public interface LikeDao {
    /**
     * Check if user already liked the post
     * @param postId the post id to search for
     * @param userId the user id to search for
     * @return true if user did like post, otherwise - false
     * @throws DataAccessException
     */
    boolean didUserLikePost(int postId, int userId) throws DataAccessException;

    /**
     * Save like
     * @param like like object to save
     * @throws DataAccessException
     */
    void saveLike(Like like) throws DataAccessException;

    /**
     * Remove like from post
     * @param postId the post id to search for
     * @param userId the user id to search for
     * @throws DataAccessException
     */
    void removeLikeFromPostByUserId(int userId, int postId) throws DataAccessException;
}
