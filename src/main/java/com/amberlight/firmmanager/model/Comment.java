package com.amberlight.firmmanager.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple JavaBean object representing a comment of {@link Post}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Entity
@Table(name = "comments")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends BaseEntity {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd.MM.yyyy, HH:mm")
    @Column(name = "comment_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date commentDate;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userAuthor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
