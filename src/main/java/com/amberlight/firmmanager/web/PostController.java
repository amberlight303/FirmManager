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
 *
 * @author Oleh Koryachenko
 * @version 1.0
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
    public String addPost(Post post){


        //if an Post's main image was uploaded
        if (!post.getImage().isEmpty()) {
            try {
                //convert MultipartFile into bytes
                byte[] bytes = post.getImage().getBytes();
                //Creating rootpath string to store file
                // by getting path from the context parameter from web.xml
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "postsImages";
                //Creating root directory to store file
                //make directories if there is no such directories
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                //random string creator for making new random photo file name
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                //new server file
                File newServerFile;
                //file name without extension
                String fileName;
                //file name with extension
                String fullFileName;
                //files analyzer for inspection saving directory for
                // an existence of duplicates of the just generated photo's file name
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                //create a new File until its name will be original in the directory
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(post.getImage().getOriginalFilename());
                    // Create the file on server
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                            fullFileName,
                            directory));

                //get output stream for writing bytes of the image
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                //write bytes
                stream.write(bytes);
                stream.close();
                //set post's image file name with name of just created image file
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
    public String initAddPostForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/createOrUpdatePost";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * editing a <code>Post</code> in the data store.
     */
    @RequestMapping(value = "/admin/posts/{postId}/editPost", method = RequestMethod.POST)
    public String editPost(Post post, @PathVariable("postId") int postId){
        //if a Post's main image was uploaded
        if (!post.getImage().isEmpty()) {
            try {
                //convert MultipartFile into bytes
                byte[] bytes = post.getImage().getBytes();
                //Creating rootpath string to store file
                // by getting path from the context parameter from web.xml
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "postsImages";
                //delete the old post's photo if it is
                if(post.getOldPostImgName()!=null){
                    File oldPostPhoto = new File(rootPath + File.separator + post.getOldPostImgName());
                    oldPostPhoto.delete();
                }
                //Creating root directory to store file
                //make directories if there is no such directories
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                //random string creator for making new random photo file name
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                //new server file
                File newServerFile;
                //file name without extension
                String fileName;
                //file name with extension
                String fullFileName;
                //files analyzer for inspection saving directory for
                // an existence of duplicates of the just generated photo's file name
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                //create a new File until its name will be original in the directory
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(post.getImage().getOriginalFilename());
                    // Create the file on server
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                        fullFileName,
                        directory));
                //get output stream for writing bytes of the image
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                //write bytes
                stream.write(bytes);
                stream.close();
                //set post's image file name with name of just created image file
                post.setImageFileName(fullFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        } else {
            //if new post's main image has not been uploaded,
            // set post's image file name with old value
            post.setImageFileName(this.firmManagerService.findPostById(postId).getImageFileName());
        }
        post.setPostDate(new Date());
        post.setAmountOfLikes(this.firmManagerService.findPostById(postId).getAmountOfLikes());
        this.firmManagerService.savePost(post);
        return "redirect:/posts/{postId}";
    }

    /**
     * Handling GET request for getting form for editing <code>Post</code>.
     */
    @RequestMapping(value = "/admin/posts/{postId}/editPost", method = RequestMethod.GET)
    public String initEditPostForm(Model model, @PathVariable("postId") int postId){
        //get post for initiation a post's edit form
        model.addAttribute("post", this.firmManagerService.findPostById(postId));
        return "posts/createOrUpdatePost";
    }

    /**
     * Handling GET request for showing a particular <code>Post</code> with details.
     */
    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public String showPost(@PathVariable int postId, Model model){
        //get post with comments
        Post post = this.firmManagerService.findPostByIdFetchComments(postId);
        if (post == null) return "test/oops";
        model.addAttribute("post", post);
        //initiation of a comment's add form
        model.addAttribute("comment", new Comment());
        return "posts/post";
    }

    /**
     * Handling GET request for getting a certain page of posts.
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public String showPage(@PathVariable("page") int page, Model model){
        long numberOfPages = 0;
        long numberOfPosts = this.firmManagerService.countPosts();
        if(numberOfPosts != 0){
            //5 is number of posts per one page
            //a number of pages depends on a number of posts
            if(numberOfPosts % 5 == 0){
                numberOfPages = numberOfPosts/5;
            } else {
                numberOfPages = numberOfPosts/5+1;
            }
            //url exceptional input control
            if (page>numberOfPages || page < 0 || page == 0) return "test/oops";
            //get a list of posts for a particular page
            List<Post> posts = this.firmManagerService.findPosts(page,(int) numberOfPosts);
            //reverse list for a correct displaying
            //most newer posts should be displayed at first
            Collections.reverse(posts);
            model.addAttribute("posts", posts);
            //model attributes below are for displaying the page navigation element
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
    public String processDeletePost(@PathVariable("postId") int postId){
        Post post = this.firmManagerService.findPostById(postId);
        if(post.getImageFileName()!=null){
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
        //get current user
        User user = userService.findByUsername(currentUserUsername);
        if(this.firmManagerService.didUserLikePost(postId, user.getId())) {
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
