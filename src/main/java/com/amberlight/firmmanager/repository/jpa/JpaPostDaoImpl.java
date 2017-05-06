package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.PostDao;
import com.amberlight.firmmanager.model.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * JPA implementation of {@link PostDao} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Repository("postDao")
public class JpaPostDaoImpl implements PostDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void savePost(Post post) {
        if (post.getId() == null) {
            this.em.persist(post);
        } else {
            this.em.merge(post);
        }
    }

    @Override
    public Long countPosts() {
        Query query = this.em.createQuery("SELECT COUNT(post) FROM Post post");
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Post> findPosts() {
        Query query = this.em.createQuery("SELECT post FROM Post post");
        return query.getResultList();
    }

    @Override
    public Post findPostById(int id) {
        try {
            return this.em.find(Post.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Post> findPosts(int page, int numberOfPosts) {
        Query query = this.em.createQuery("SELECT post FROM Post post");
        int offset = numberOfPosts-(page*5);
        query.setFirstResult((offset<0)?0:offset);
        query.setMaxResults((offset<0)?5+offset:5);
        return query.getResultList();
    }

    @Override
    public void updatePostImage(String filename, int postId) {
        Query query = this.em.createNativeQuery("UPDATE posts SET image_filename = ? WHERE id = ?");
        query.setParameter(1, filename);
        query.setParameter(2, postId);
        query.executeUpdate();
    }

    @Override
    public void deletePost(int postId) {
        Query query1 = this.em.createNativeQuery("DELETE FROM likes WHERE post_id = ?");
        query1.setParameter(1, postId);
        query1.executeUpdate();
        Query query2 = this.em.createNativeQuery("DELETE FROM comments WHERE post_id = ?");
        query2.setParameter(1, postId);
        query2.executeUpdate();
        Post post = this.em.find(Post.class, postId);
        this.em.remove(post);
    }

    @Override
    public Post findPostByIdFetchComments(int id) {
        Query query = this.em.createQuery("SELECT post FROM Post post LEFT JOIN FETCH post.comments c " +
                "LEFT JOIN FETCH c.userAuthor u LEFT JOIN FETCH u.employee " +
                "WHERE post.id = :id");
        query.setParameter("id", id);
        return (Post) query.getSingleResult();
    }

    @Override
    public Post findPostByIdFetchLikes(int id) throws DataAccessException {
        Query query = this.em.createQuery("SELECT post FROM Post post LEFT JOIN FETCH post.likes " +
                "WHERE post.id = :id");
        query.setParameter("id", id);
        try {
            return (Post) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void updateAmountOfLikes(int postId, int amountOfLikes) throws DataAccessException {
        Query query = this.em.createNativeQuery("UPDATE posts SET amount_of_likes = ? WHERE id = ?");
        query.setParameter(1, amountOfLikes);
        query.setParameter(2, postId);
        query.executeUpdate();
    }
}
