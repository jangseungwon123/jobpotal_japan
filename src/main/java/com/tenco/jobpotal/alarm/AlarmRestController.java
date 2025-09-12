package com.tenco.jobpotal.alarm;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@Slf4j
public class AlarmRestController {

    private final AlarmService alarmService;

    /**
     * 사용자별 알람 목록 조회
     */
    @GetMapping("/user")
    public ResponseEntity<?> getAlarmsByUserId(
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        log.info("알람 목록 조회 API 호출 - 사용자ID: {}", loginUser.getId());

        List<AlarmResponse.ListDTO> alarms = alarmService.getAlarmsByUserId(loginUser.getId());

        log.info("API 응답 데이터 개수: {}", alarms.size());
        if (!alarms.isEmpty()) {
            log.info("첫 번째 응답 데이터: {}", alarms.get(0));
        }

        return ResponseEntity.ok(new ApiUtil<>(alarms));
    }

    /**
     * 알람 읽음 처리
     */
    @PutMapping("/read")
    public ResponseEntity<?> updateReadStatus(
            @RequestBody AlarmRequest.UpdateReadStatusDTO dto,
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        alarmService.updateReadStatus(dto);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    /**
     * 읽지 않은 알람 개수 조회
     */
    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount(
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        Long count = alarmService.getUnreadCount(loginUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(count));
    }
}
