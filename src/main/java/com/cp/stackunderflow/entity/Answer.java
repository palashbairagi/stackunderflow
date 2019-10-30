package com.cp.stackunderflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;

    @JsonIgnore
    private String status;

    @ApiModelProperty(required = false, hidden = true)
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ApiModelProperty(required = false, hidden = true)
    @ManyToOne
    private User user;

    @Column(name = "question_id")
    private Integer questionId;

    @ApiModelProperty(required = false, hidden = true)
    @OneToMany
    @JoinColumn(name = "answer_id")
    private List<AnswerComment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setLastUpdate(LocalDateTime lastUpdated) {
        this.lastUpdate = lastUpdated;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<AnswerComment> getComments() {
        return comments;
    }

    public void setComments(List<AnswerComment> comments) {
        this.comments = comments;
    }

}
