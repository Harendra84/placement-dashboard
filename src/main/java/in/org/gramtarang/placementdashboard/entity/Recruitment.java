package in.org.gramtarang.placementdashboard.entity;

import in.org.gramtarang.placementdashboard.common.DriveType;
import in.org.gramtarang.placementdashboard.common.ModeOfDrive;
import in.org.gramtarang.placementdashboard.common.RecruiterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recruitmentId;
    private String nameOfRecruiters;
    private String jobTitle;
    private String eligibility;
    private String skillSet;
    private String selectionProcess;
    private String CTC;
    private String locationOfPosting;
    private RecruiterType recruiterType;
    private LocalDateTime lastDateOfRegistration;
    private LocalDateTime tentativeDateOfRegistration;
    private ModeOfDrive modeOfDrive;
    private String anyOtherInfo;
    private String registrationLink;
    private DriveType driveType;
    private String pursuitManager;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
