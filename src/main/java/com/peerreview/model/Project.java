package com.peerreview.model;

import java.util.List;

public class Project {

    private int id;
    private String title;
    private String description;
    private String githubLink;
    private String studentEmail;

    private List<String[]> mediaList; // [file_name, media_type]

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public List<String[]> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<String[]> mediaList) {
        this.mediaList = mediaList;
    }
}