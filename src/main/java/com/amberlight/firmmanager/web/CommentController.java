package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Comment;
import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.SecurityService;
import com.amberlight.firmmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

/**
 * A controller for pages related with the {@link Comment} entity.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FirmManagerService firmManagerService;

    /**
     * Handling asynchronous requests with WebSocket for adding a comment to the post and sharing
     */
    @MessageMapping("/posts/{postId}/addComment")
    @SendTo("/topic/posts/{postId}")
    public Comment saveAndShareComment(@DestinationVariable int postId, Comment comment, Principal principal) throws Exception {
        Date date = new Date();
        comment.setCommentDate(date);
        Post post = this.firmManagerService.findPostById(postId);
        //attach comment to the post
        comment.setPost(post);
        String currentUserUsername = principal.getName();
        //get current user
        User user = userService.findByUsername(currentUserUsername);
        //set comment's author with the User
        comment.setUserAuthor(user);
        String commentText = comment.getText();
        commentText = commentText.replaceAll("<","&lt;");
        commentText = commentText.replaceAll(">","&gt;");
        commentText = comment.getText().replaceAll("\n", "<br>");
        comment.setText(commentText);
        this.firmManagerService.saveComment(comment);
        return comment;
    }

}
