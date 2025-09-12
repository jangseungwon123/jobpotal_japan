package com.tenco.jobpotal.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class AlarmResponse {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListDTO {
        private Long alarmId;
        private String content;
        private boolean isRead;
        private Timestamp createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDTO {
        private Long alarmId;
        private Long userId;
        private String content;
        private boolean isRead;
        private Timestamp createdAt;
    }

}
