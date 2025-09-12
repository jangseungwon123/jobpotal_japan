package com.tenco.jobpotal.faq;

import lombok.Builder;
import lombok.Data;


@Data
public class FAQRequestDTO {
    private String title;
    private String content;
}
