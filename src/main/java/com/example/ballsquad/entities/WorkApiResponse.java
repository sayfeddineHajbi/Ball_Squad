package com.example.ballsquad.entities;

import java.util.List;

public class WorkApiResponse {
    private List<WorkData> works;

    public List<WorkData> getWorks() {
        return works;
    }

    public void setWorks(List<WorkData> works) {
        this.works = works;
    }

    public static class WorkData {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
