package com.tenco.jobpotal.alarm.event;

import com.tenco.jobpotal.alarm.AlarmRequest;
import com.tenco.jobpotal.alarm.AlarmService;
import com.tenco.jobpotal.subscribe.CompSub;
import com.tenco.jobpotal.subscribe.CompSubJpaRepository;
import com.tenco.jobpotal.subscribe.UserSub;
import com.tenco.jobpotal.subscribe.UserSubJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventListener {

    private final AlarmService alarmService;
    private final CompSubJpaRepository compSubRepository;
    private final UserSubJpaRepository userSubRepository;

    /**
     * 채용공고 작성 이벤트 처리
     * 구독한 사용자들에게 알람 발송
     */
    @EventListener
    public void handleJobPostCreatedEvent(JobPostCreatedEvent event) {
        log.info("채용공고 작성 이벤트 처리 시작 - 회사 이름: {}, 채용공고ID: {}",
                event.getCompanyName(), event.getRecruitId());

        try {
            // 해당 회사를 구독한 사용자들 조회
            List<UserSub> subscribers = userSubRepository.findAllByUserAndCompanyId(event.getCompId());
            
            log.info("구독자 수: {}", subscribers.size());

            // 포문으로 각 구독자에게 알람 생성
            for (UserSub subs : subscribers) {
                String alarmContent = String.format("%s에서 새로운 채용공고 '%s'가 등록되었습니다.",
                        event.getCompanyName(),// 구독 대상 회사의 이름이 %s에 포맷팅 됨.
                        event.getJobPostTitle());// 채용공고의 제목이 %s에 포맷팅됨

                // 알람 리퀘스트 DTO(구독자 id, 알람내용) 생성
                // TODO: 빌더패턴으로 바꾸기
                AlarmRequest.CreateDTO createDTO = new AlarmRequest.CreateDTO(
                        subs.getUser().getUserId(),
                        alarmContent
                );

                alarmService.createAlarm(createDTO);
                
                log.debug("알람 생성 완료 - 사용자ID: {}, 내용: {}", 
                        subs.getUser().getUserId(), alarmContent);
            }

            log.info("채용공고 작성 이벤트 처리 완료 - 총 {}명에게 알람 발송", subscribers.size());

        } catch (Exception e) {
            log.error("채용공고 작성 이벤트 처리 중 오류 발생", e);
        }
    }
}
