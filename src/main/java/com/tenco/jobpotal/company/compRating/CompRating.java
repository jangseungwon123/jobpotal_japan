package com.tenco.jobpotal.company.compRating;


import com.tenco.jobpotal.user.applInfo.ApplInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comp_rating")
public class CompRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applInfo_id")
    private ApplInfo applInfo;

    @Column(nullable = false)
    private int score;

}
