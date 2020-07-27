package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Comment;
import com.amberlight.firmmanager.model.Like;
import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.UserService;
import com.amberlight.firmmanager.util.FilesAnalyzer;
import com.amberlight.firmmanager.util.RandomStringCreator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A controller for pages related with the {@link Post} entity.
 */
@Controller
public class PostController{

    @Autowired
    private UserService userService;

    @Autowired
    private FirmManagerService firmManagerService;

    @Value("${uploads.rootPath}")
    private String uploadRootPath;

    /**
     * This method will be called on form submission, handling POST request for
     * adding a <code>Post</code> to the data store.
     */
    @RequestMapping(value = "/admin/posts/addPost", method = RequestMethod.POST)
    public String addPost(Post post) {
        if (!post.getImage().isEmpty()) {
            try {
                byte[] bytes = post.getImage().getBytes();

                String rootPath = this.uploadRootPath
                        + File.separator
                        + "postsImages";
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                File newServerFile;
                String fileName;
                String fullFileName;
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(post.getImage().getOriginalFilename());
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                            fullFileName,
                            directory));
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                stream.write(bytes);
                stream.close();
                post.setImageFileName(fullFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        }
        post.setPostDate(new Date());
        this.firmManagerService.savePost(post);
        return "redirect:/";
    }

    /**
     * Handling GET request for getting form for adding a <code>Post</code>.
     */
    @RequestMapping(value = "/admin/posts/addPost", method = RequestMethod.GET)
    public String initAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/createOrUpdatePost";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * editing a <code>Post</code> in the data store.
     */
    @RequestMapping(value = "/admin/posts/{postId}/editPost", method = RequestMethod.POST)
    public String editPost(Post post, @PathVariable("postId") int postId) {
        Post dbPost = this.firmManagerService.findPostById(postId);
        if (dbPost == null) throw new IllegalStateException("post doesn't exist");
        if (!post.getImage().isEmpty()) {
            try {
                byte[] bytes = post.getImage().getBytes();
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "postsImages";
                if (post.getOldPostImgName()!=null) {
                    File oldPostPhoto = new File(rootPath + File.separator + post.getOldPostImgName());
                    oldPostPhoto.delete();
                }
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                File newServerFile;
                String fileName;
                String fullFileName;
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                do {
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(post.getImage().getOriginalFilename());
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                        fullFileName,
                        directory));
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                stream.write(bytes);
                stream.close();
                post.setImageFileName(fullFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        } else {
            post.setImageFileName(this.firmManagerService.findPostById(postId).getImageFileName());
        }
        post.setPostDate(dbPost.getPostDate());
        post.setPostUpdateDate(new Date());
        post.setAmountOfLikes(this.firmManagerService.findPostById(postId).getAmountOfLikes());
        this.firmManagerService.savePost(post);
        return "redirect:/posts/{postId}";
    }

    /**
     * Handling GET request for getting form for editing <code>Post</code>.
     */
    @RequestMapping(value = "/admin/posts/{postId}/editPost", method = RequestMethod.GET)
    public String initEditPostForm(Model model, @PathVariable("postId") int postId) {
        model.addAttribute("post", this.firmManagerService.findPostById(postId));
        return "posts/createOrUpdatePost";
    }

    /**
     * Handling GET request for showing a particular <code>Post</code> with details.
     */
    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public String showPost(@PathVariable int postId, Model model) {
        Post post = this.firmManagerService.findPostByIdFetchComments(postId);
        if (post == null) return "test/oops";
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "posts/post";
    }

    /**
     * Handling GET request for getting a certain page of posts.
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String showPage(@PathVariable("page") int page, Model model) {
        long numberOfPages = 0;
        long numberOfPostsPerPage = this.firmManagerService.countPosts();
        if (numberOfPostsPerPage != 0) {
            if (numberOfPostsPerPage % 5 == 0) {
                numberOfPages = numberOfPostsPerPage/5;
            } else {
                numberOfPages = numberOfPostsPerPage/5+1;
            }
            if (page>numberOfPages || page < 0 || page == 0) return "test/oops";
            List<Post> posts = this.firmManagerService.findPosts(page,(int) numberOfPostsPerPage);
            model.addAttribute("posts", posts);
            model.addAttribute("pagesOnTheLeft", page-1);
            model.addAttribute("pagesOnTheRight", numberOfPages-page);
            model.addAttribute("currentPage", page);
            model.addAttribute("numberOfPages", numberOfPages);
            return "posts/postsList";
        }
        return "posts/postsList";
    }

    /**
     * Handling POST request for deleting <code>Post</code>.
     */
    @RequestMapping(value = "/admin/posts/{postId}/deletePost", method = RequestMethod.POST)
    public String processDeletePost(@PathVariable("postId") int postId) {
        Post post = this.firmManagerService.findPostById(postId);
        if (post.getImageFileName()!=null) {
            File imgLocation = new File(this.uploadRootPath
                    + File.separator + "postsImages" + File.separator + post.getImageFileName());
            imgLocation.delete();
        }
        this.firmManagerService.deletePost(postId);
        return "redirect:/";
    }

    /**
     * Handling POST request for setting like to the post <code>Post</code>.
     */
    @RequestMapping(value = "/posts/{postId}/like", method = RequestMethod.POST)
    @ResponseBody
    public String processLikePost(@PathVariable("postId") int postId) {
        Post post = this.firmManagerService.findPostById(postId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();
        User user = userService.findByUsername(currentUserUsername);
        if (this.firmManagerService.didUserLikePost(postId, user.getId())) {
            this.firmManagerService.removeLikeFromPostByUserId(user.getId(), postId);
            post.setAmountOfLikes(post.getAmountOfLikes() - 1);
        } else {
            Like like = new Like();
            like.setAuthor(user);
            like.setPost(post);
            this.firmManagerService.saveLike(like);
            post.setAmountOfLikes(post.getAmountOfLikes() + 1);
        }
        int numberOfLikes = post.getAmountOfLikes();
        this.firmManagerService.updateAmountOfLikes(postId, numberOfLikes);
        return ""+numberOfLikes;
    }

    @Override
    public String toString() {
        return "PostController{" +
                "userService=" + userService +
                ", firmManagerService=" + firmManagerService +
                ", uploadRootPath='" + uploadRootPath + '\'' +
                '}';
    }
}
