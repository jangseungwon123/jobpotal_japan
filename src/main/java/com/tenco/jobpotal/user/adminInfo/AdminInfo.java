package com.tenco.jobpotal.user.adminInfo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "admin_info")
public class AdminInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true, nullable = false)
    private String adminLoginId;

    @Column(nullable = false)
    private String adminPassword;

    @Column(nullable = false)
    private String adminName;

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String adminPhone;

    @Builder
    public AdminInfo(Long adminId, String adminLoginId, String adminPassword, String adminName, String adminEmail, String adminPhone) {
        this.adminId = adminId;
        this.adminLoginId = adminLoginId;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPhone = adminPhone;
    }

}
