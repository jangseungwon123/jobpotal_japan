package com.tenco.jobpotal.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AlarmRequest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDTO {
        private Long userId;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReadStatusDTO {
        private Long alarmId;
        private boolean isRead;
    }
}
