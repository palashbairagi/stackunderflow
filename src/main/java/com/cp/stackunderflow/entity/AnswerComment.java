package com.cp.stackunderflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer_comment")
public class AnswerComment {

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

    @Column(name = "answer_id")
    private Integer answerId;

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

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

}
