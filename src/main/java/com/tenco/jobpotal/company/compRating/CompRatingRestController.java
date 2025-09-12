package com.tenco.jobpotal.company.compRating;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CompRatingRestController {

    private final CompRatingService compRatingService;

    @Operation(summary = "평점주기", description = "합격한 유저에 한해 평점을 주는 기능")
    @PostMapping("/comprating/save")
    public ResponseEntity<?> save(@Valid @RequestBody CompRatingRequest.SaveDTO saveDTO, Errors errors,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser){
        compRatingService.save(saveDTO,loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>("평점을 남기셨습니다."));
    }

    @Operation(summary = "평점수정", description = "부여한 평점을 수정하는 기능")
    @PutMapping("/comprating/update")
    public ResponseEntity<?> update(@Valid @RequestBody CompRatingRequest.UpdateDTO updateDTO, Errors errors,
                                    @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser){
        compRatingService.update(updateDTO.getRatingId(), updateDTO.getScore(), loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>("평점 수정 완료하셨습니다."));
    }

    @Operation(summary = "평점 삭제" , description = "부여한 평점을 삭제하는 기능")
    @DeleteMapping("/comprating/{ratingId}/delete")
    public ResponseEntity<?> delete(@PathVariable(name = "ratingId") Long ratingId,
            @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser){
        compRatingService.delete(ratingId,loginUser);
        return ResponseEntity.ok(new ApiUtil<>("삭제 완료"));
    }

    @Operation(summary = "기업 평점", description = "기업의 평점을 보여주는 기능")
    @GetMapping("/comprating/{id}/average")
    public ResponseEntity<?> getAverageRating(@PathVariable(name = "id") Long id){
        CompRatingResponse.AvgScoreDTO avgScoreDTO = compRatingService.getAverageScore(id);
        return ResponseEntity.ok(new ApiUtil<>(avgScoreDTO));
    }



}
