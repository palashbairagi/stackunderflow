package com.cp.stackunderflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String body;

    @JsonIgnore
    private String status;

    @ApiModelProperty(required = false, hidden = true)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ApiModelProperty(required = false, hidden = true)
    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name = "question_tags",
            joinColumns = { @JoinColumn(name = "question_id")},
            inverseJoinColumns = { @JoinColumn(name = "tag_id")} )
    private Set<Tag> tags;

    @ApiModelProperty(required = false, hidden = true)
    @OneToMany
    @JoinColumn(name = "question_id")
    private List<QuestionComment> comments;

    @ApiModelProperty(required = false, hidden = true)
    @OneToMany
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime last_updated) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<QuestionComment> getComments() {
        return comments;
    }

    public void setComments(List<QuestionComment> comments) {
        this.comments = comments;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

}
