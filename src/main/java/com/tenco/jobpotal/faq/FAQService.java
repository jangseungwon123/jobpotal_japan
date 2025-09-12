package com.tenco.jobpotal.faq;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.adminInfo.AdminInfo;
import com.tenco.jobpotal.user.adminInfo.AdminInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FAQService {

    private final FAQRepository faqRepository; // [수정] FAQRepository 의존성 주입이 누락되었습니다.
    private final AdminInfoJpaRepository adminInfoJpaRepository;

    // 전체 목록
    public List<FAQResponseDTO> getAllFAQs() {

        List<FAQInfo> faqInfoList = faqRepository.findAll();

        List<FAQResponseDTO> faqInfoListDTO = new ArrayList<>();

        for (FAQInfo faqInfo : faqInfoList) {
            FAQResponseDTO faqResponseDTO = new FAQResponseDTO();
            faqInfoListDTO.add(faqResponseDTO.toDTO(faqInfo));
        }

        return faqInfoListDTO;


    }

    // 상세 조회
    public FAQResponseDTO getFAQ(Long id) {
        FAQInfo faq = faqRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 FAQ를 찾을 수 없습니다."));


        return new FAQResponseDTO().toDTO(faq);
    }

    // 등록
    @Transactional
    public void createFAQ(FAQRequestDTO dto, LoginUser loginUser) {
        // [보안] 서비스 레이어에서 관리자 권한 확인
        if (!loginUser.isAdmin()) {
            throw new Exception403("FAQ를 등록할 권한이 없습니다.");
        }

        AdminInfo adminInfo = adminInfoJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new Exception404("관리자 정보를 찾을 수 없습니다."));

        FAQInfo faq = FAQInfo.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .adminInfo(adminInfo)
                .build();

        faqRepository.save(faq);
    }

    // 수정
    @Transactional
    public FAQResponseDTO updateFAQ(Long id, FAQRequestDTO dto, LoginUser loginUser) {
        // [보안] 서비스 레이어에서 관리자 권한 확인
        if (!loginUser.isAdmin()) {
            throw new Exception403("FAQ를 수정할 권한이 없습니다.");
        }

        FAQInfo faq = faqRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 FAQ를 찾을 수 없습니다."));
        faq.setTitle(dto.getTitle());
        faq.setContent(dto.getContent());


        return new FAQResponseDTO().toDTO(faq);
    }

    // 삭제
    @Transactional
    public void deleteFAQ(Long id, LoginUser loginUser) {
        // [보안] 서비스 레이어에서 관리자 권한 확인
        if (!loginUser.isAdmin()) {
            throw new Exception403("FAQ를 삭제할 권한이 없습니다.");
        }
        FAQInfo faq = faqRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 FAQ를 찾을 수 없습니다."));
        faqRepository.delete(faq);
    }


}
