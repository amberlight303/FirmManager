package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.CommentDao;
import com.amberlight.firmmanager.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * JPA implementation of {@link CommentDao} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Repository("commentDao")
public class JpaCommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveComment(Comment comment) {
        if (comment.getId() == null) {
            this.em.persist(comment);
        } else {
            this.em.merge(comment);
        }
    }

    @Override
    public void deleteCommentsByUserId(int userId) {
        Query query =  this.em.createNativeQuery("DELETE FROM comments WHERE user_id = ?");
        query.setParameter(1, userId);
        query.executeUpdate();
    }
}
