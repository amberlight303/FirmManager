package com.amberlight.firmmanager.model;

import javax.persistence.*;

/**
 * A simple JavaBean object representing a comment of {@link Like}.
 */
@Entity
@Table(name = "likes")
public class Like extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Like{" +
                "post=" + post +
                ", author=" + author +
                ", id=" + id +
                '}';
    }
}
