package com.amberlight.firmmanager.repository;


import com.amberlight.firmmanager.model.Post;
import org.springframework.dao.DataAccessException;
import java.util.Collection;
import java.util.List;

/**
 * Repository interface for objects of {@link Post} class.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public interface PostDao {
    /**
     * Save <code>Post</code> to the data store, either inserting or updating it.
     * @param post the <code>Post</code> to save
     * @throws DataAccessException
     */
    void savePost(Post post) throws DataAccessException;

    /**
     * Retrieve a collection of all <code>Post</code>s.
     * @return a collection of all <code>Post</code>s (or empty collection if none found)
     * @throws DataAccessException
     */
    Collection<Post> findPosts() throws DataAccessException;

    /**
     * Retrieve a collection of specific <code>Posts</code>s for pagination task.
     * Offset = numberOfPosts - (page * customPageSize)
     * @param page certain page for calculating offset of records in the data store
     * @param numberOfPosts number of posts for calculating offset of records in the data store
     * @return list of selected <code>Post</code>s
     * @throws DataAccessException
     */
    List<Post> findPosts(int page, int numberOfPosts) throws DataAccessException;

    /**
     * Retrieve a <code>Post</code> without fetching comments by its id from the data store.
     * @param id the <code>Post</code>'s id to search for
     * @return the <code>Post</code> if found or null if not
     * @throws DataAccessException
     */
    Post findPostById(int id) throws DataAccessException;

    /**
     * Retrieve a <code>Post</code> with fetching comments by its id from the data store.
     * @param id the <code>Post</code>'s id to search for
     * @return the <code>Post</code> if found or null if not
     * @throws DataAccessException
     */
    Post findPostByIdFetchComments(int id) throws DataAccessException;

    /**
     * Retrieve a <code>Post</code> with fetching likes by its id from the data store.
     * @param id the <code>Post</code>'s id to search for
     * @return the <code>Post</code> if found or null if not
     * @throws DataAccessException
     */
    Post findPostByIdFetchLikes(int id) throws DataAccessException;

    /**
     * Count number of <code>Post</code>s in the data store.
     * @return number of <code>Post</code>s
     * @throws DataAccessException
     */
    Long countPosts() throws DataAccessException;

    /**
     * Update file name of <code>Post</code>'s main image in the data store.
     * @param filename the file name of <code>Post</code>'s main image
     * @param postId the id to search for
     * @throws DataAccessException
     */
    void updatePostImage(String filename, int postId) throws DataAccessException;

    /**
     * Delete <code>Post</code> from the data store.
     * @param postId the id to search for
     * @throws DataAccessException
     */
    void deletePost(int postId) throws DataAccessException;

    /**
     * Update <code>Post</code>'s amount of likes field.
     * @param postId the id to search for
     * @param amountOfLikes amount of likes to insert
     * @throws DataAccessException
     */
    void updateAmountOfLikes(int postId, int amountOfLikes)  throws DataAccessException;

}
