package com.tenco.jobpotal.faq;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FAQRestController {

    private final FAQService faqService;

    // 전체 목록
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<FAQResponseDTO> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok(new ApiUtil<>(faqs));
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        FAQResponseDTO faq = faqService.getFAQ(id);
        return ResponseEntity.ok(new ApiUtil<>(faq));
    }

    // 등록 (관리자만)
    @PostMapping("/save")
    public ResponseEntity<?> create(@RequestBody FAQRequestDTO dto, @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        // 서비스 레이어에서 권한 확인 및 생성
        faqService.createFAQ(dto, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>("등록이 완료 되었습니다."));
    }

    // 수정 (관리자만)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FAQRequestDTO dto, @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        FAQResponseDTO responseDTO = faqService.updateFAQ(id, dto, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 삭제 (관리자만)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        faqService.deleteFAQ(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("FAQ가 삭제되었습니다."));
    }
}
