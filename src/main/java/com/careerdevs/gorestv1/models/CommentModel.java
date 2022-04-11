package com.careerdevs.gorestv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentModel {
    private int id;
    @JsonProperty("post_id")
    private int postId;
    private String name;
    private String email;
    private String body;

    //Dont delete, keep your default construction just in case
    public CommentModel() {

    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", post_id=" + postId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
