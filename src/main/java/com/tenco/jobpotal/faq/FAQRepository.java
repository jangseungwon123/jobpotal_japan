package com.tenco.jobpotal.faq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQInfo, Long> {
    // JpaRepository를 상속받으면 기본적인 CRUD 메서드(findAll, findById, save, delete 등)가 자동으로 제공됩니다.
}