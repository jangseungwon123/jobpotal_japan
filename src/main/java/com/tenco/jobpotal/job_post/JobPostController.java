package com.tenco.jobpotal.job_post;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/jobposts")
@Tag(name = "JobPost", description = "企業求人公告API")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    // 전체 목록 조회
    @Operation(summary = "求人公告一覧")
    @GetMapping
    public ResponseEntity<List<JobPostResponseDTO>> getAllJobPosts() {
        List<JobPostResponseDTO> jobPosts = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPosts);
    }

    // 상세 보기 조회
    @Operation(summary = "求人公告詳細表示")
    @GetMapping("/{recruitId}")
    public ResponseEntity<JobPostResponseDTO> getJobPostById(@PathVariable Long recruitId) {
        JobPost jobPost = jobPostService.getJobPostById(recruitId); // 이 메서드는 예외 던지므로 Optional 아님
        return ResponseEntity.ok(new JobPostResponseDTO(jobPost));
    }


    // 게시글 등록
    @Operation(summary = "求人公告登録")
    @PostMapping("/save")
    public ResponseEntity<JobPostResponseDTO> createJobPost(@RequestBody @Valid JobPostRequestDTO requestDTO,
                                                            @RequestAttribute(value = Define.LOGIN_USER) LoginUser loginUser) {
        // [보안 강화] 기업 회원만 공고를 등록할 수 있도록 확인
        if (!loginUser.isCompany()) {
            throw new Exception403("기업회원만 채용공고를 등록할 수 있습니다.");
        }
        JobPostResponseDTO created = jobPostService.createJobPost(requestDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 게시글 수정
    @Operation(summary = "求人公告修正")
    @PutMapping("/update/{recruitId}")
        public ResponseEntity<JobPostResponseDTO> updateJobPost(@PathVariable Long recruitId,
                                                                @RequestBody @Valid JobPostRequestDTO requestDTO,
        @RequestAttribute(value = Define.LOGIN_USER)LoginUser loginUser){
            JobPostResponseDTO updated = jobPostService.updateJobPost(recruitId, requestDTO, loginUser);
        return ResponseEntity.ok(updated);
    }


    // 게시글 삭제
    @Operation(summary = "求人公告削除")
    @DeleteMapping("/delete/{recruitId}")
    public ResponseEntity<?> deleteJobPost(@PathVariable Long recruitId,
                                              @RequestAttribute(value = Define.LOGIN_USER) LoginUser loginUser) {
        // 서비스 계층에 로그인 정보를 넘겨 권한을 확인하도록 합니다.
        jobPostService.deleteJobPost(recruitId, loginUser);
        System.out.println("### deleteJobPost: 새 코드가 실행되었습니다! ###"); // 진단용 로그 추가
        return ResponseEntity.ok(new ApiUtil<>("채용공고 삭제가 완료 되었습니다."));
    }


    // 페이징 처리

    @GetMapping("/paged")
    @Operation(summary = "ページング機能")
    public ResponseEntity<Page<JobPostResponseDTO>> getPagedJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<JobPostResponseDTO> pagedResult = jobPostService.getJobPostsPaged(pageable);
        return ResponseEntity.ok(pagedResult);
    }

    // 검색 기능
    @Operation(summary = "検索")
    @GetMapping("/search")
    public ResponseEntity<List<JobPostResponseDTO>> searchJobPosts(@RequestParam String keyword) {
        List<JobPostResponseDTO> result = jobPostService.searchJobPosts(keyword);
        return ResponseEntity.ok(result);
    }
}
