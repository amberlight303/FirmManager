package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Comment;
import com.amberlight.firmmanager.model.Post;
import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.SecurityService;
import com.amberlight.firmmanager.service.UserService;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

/**
 * A controller for pages related with the {@link Comment} entity.
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
     * Handling asynchronous requests with WebSocket for adding a comment to the post, shares comment among all subs
     */
    @MessageMapping("/posts/{postId}/addComment")
    @SendTo("/topic/posts/{postId}")
    public Comment saveAndShareComment(@DestinationVariable int postId, Comment comment, Principal principal) {
        Date date = new Date();
        comment.setCommentDate(date);
        Post post = this.firmManagerService.findPostById(postId);
        comment.setPost(post);
        String currentUserUsername = principal.getName();
        User user = userService.findByUsername(currentUserUsername);
        comment.setUserAuthor(user);
        comment.setText(Encode.forHtml(comment.getText()));
        this.firmManagerService.saveComment(comment);
        return comment;
    }

    @Override
    public String toString() {
        return "CommentController{" +
                "userService=" + userService +
                ", securityService=" + securityService +
                ", firmManagerService=" + firmManagerService +
                '}';
    }
}
