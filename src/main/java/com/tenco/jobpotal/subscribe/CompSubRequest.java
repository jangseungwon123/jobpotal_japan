package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal.company.CompInfo;
import lombok.Data;

public class CompSubRequest {

    @Data
    public static class SaveDTO {
        private Long userId;
        private Long compId;

    }
    public static class SubListDTO{
        private Long compInfoId;
    }

}
