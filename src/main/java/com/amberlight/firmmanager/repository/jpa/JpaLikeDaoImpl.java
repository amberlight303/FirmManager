package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.model.Like;
import com.amberlight.firmmanager.repository.LikeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;

/**
 * JPA implementation of {@link LikeDao} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Repository("LikeDao")
public class JpaLikeDaoImpl implements LikeDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public void saveLike(Like like) {
        if (like.getId() == null) {
            this.em.persist(like);
        } else {
            this.em.merge(like);
        }
    }

    @Override
    public void removeLikeFromPostByUserId(int userId, int postId) {
        Query query = this.em.createNativeQuery("DELETE FROM likes WHERE user_id = ? and post_id = ?");
        query.setParameter(1, userId);
        query.setParameter(2, postId);
        query.executeUpdate();

    }


    @Override
    public boolean didUserLikePost(int postId, int userId) {
        Query query = this.em.createNativeQuery("SELECT COUNT(user_id) " +
                "FROM likes WHERE user_id = ? AND post_id = ?");
        query.setParameter(1, userId);
        query.setParameter(2, postId);
        BigInteger count =(BigInteger) query.getSingleResult();
        return count.intValue() == 1;
    }
}
