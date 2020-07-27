package com.amberlight.firmmanager.model;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.util.*;

/**
 * Simple JavaBean object representing a post.
 */
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    public Post(Date postDate, String title, String content, String contentPreview, String imageFileName, int amountOfLikes, Set<Like> likes, Set<Comment> comments) {
        this.postDate = postDate;
        this.title = title;
        this.content = content;
        this.contentPreview = contentPreview;
        this.imageFileName = imageFileName;
        this.amountOfLikes = amountOfLikes;
        this.likes = likes;
        this.comments = comments;
    }

    public Post () {}

    @Column(name = "post_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date postDate;

    @Column(name = "post_update_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date postUpdateDate;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "content_preview")
    private String contentPreview;

    @Column(name = "image_filename")
    private String imageFileName;

    @Transient
    private String oldPostImgName;

    @Transient
    private MultipartFile image;

    @Column(name = "amount_of_likes")
    private int amountOfLikes;

    @OneToMany(mappedBy = "post")
    private Set<Like> likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Comment> comments;

    public Date getPostUpdateDate() {
        return postUpdateDate;
    }

    public void setPostUpdateDate(Date postUpdateDate) {
        this.postUpdateDate = postUpdateDate;
    }

    public String getContentPreview() {
        return contentPreview;
    }

    public void setContentPreview(String contentPreview) {
        this.contentPreview = contentPreview;
    }

    public int getAmountOfLikes() {
        return amountOfLikes;
    }

    public void setAmountOfLikes(int amountOfLikes) {
        this.amountOfLikes = amountOfLikes;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public String getOldPostImgName() {
        return oldPostImgName;
    }

    public void setOldPostImgName(String oldPostImgName) {
        this.oldPostImgName = oldPostImgName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected Set<Comment> getCommentsInternal() {
        if (this.comments == null) return new HashSet<>();
        return this.comments;
    }

    protected void setCommentsInternal(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        List<Comment> sortedComments = new ArrayList<>(getCommentsInternal());
        PropertyComparator.sort(sortedComments, new MutableSortDefinition("id", true, false));
        return Collections.unmodifiableList(sortedComments);
    }

    public void addComment(Comment comment) {
        getCommentsInternal().add(comment);
        comment.setPost(this);
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postDate=" + postDate +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", contentPreview='" + contentPreview + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", oldPostImgName='" + oldPostImgName + '\'' +
                ", image=" + image +
                ", amountOfLikes=" + amountOfLikes +
                ", likes=" + likes +
                ", comments=" + comments +
                ", id=" + id +
                '}';
    }
}
