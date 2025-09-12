package com.tenco.jobpotal.alarm;

import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmJpaRepository alarmRepository;
    private final UserJpaRepository userRepository;

    /**
     * 알람 생성
     */
    @Transactional
    public void createAlarm(AlarmRequest.CreateDTO dto) {

        log.info("알림 등록 처리 시작");

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception404("존재하지 않는 사용자입니다."));

        log.info("유저 조회 확인 : {}", user);
        //Todo DTO 패턴으로 바꾸기
        Alarm alarm = new Alarm();
        alarm.setUser(user);
        alarm.setContent(dto.getContent());
        alarm.setRead(false);

        Alarm savedAlarm = alarmRepository.save(alarm);

        log.info("savedAlarm : {}", savedAlarm);
    }

    /**
     * 사용자별 알람 목록 조회
     */
    public List<AlarmResponse.ListDTO> getAlarmsByUserId(Long userId) {
        List<Alarm> alarms = alarmRepository.findAllByUserId(userId);
        
        return alarms.stream()
                .map(alarm -> new AlarmResponse.ListDTO(
                        alarm.getAlarmId(),
                        alarm.getContent(),
                        alarm.isRead(),
                        alarm.getCreatedAt()
                ))
                .collect(Collectors.toList());
                
    }

    /**
     * 알람 읽음 처리
     */
    @Transactional
    public void updateReadStatus(AlarmRequest.UpdateReadStatusDTO dto) {
        Alarm alarm = alarmRepository.findById(dto.getAlarmId())
                .orElseThrow(() -> new RuntimeException("알람을 찾을 수 없습니다."));

        alarm.setRead(dto.isRead());
        alarmRepository.save(alarm);
    }

    /**
     * 읽지 않은 알람 개수 조회
     */
    public Long getUnreadCount(Long userId) {
        return alarmRepository.countUnreadByUserId(userId);
    }
}
