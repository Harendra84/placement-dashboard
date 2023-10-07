package in.org.gramtarang.placementdashboard.entity;

import in.org.gramtarang.placementdashboard.common.RoleType;
import in.org.gramtarang.placementdashboard.common.StudentChoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private boolean isActive;
    private String emailId;
    private String fullName;
    private String username;
    private String registrationNo;
    private String phoneNo;
    private String password;
    private String gender;
    private String campus;
    private String school;
    private String branch;
    private double currentCGPA;
    private int backlogs;
    private double marks10th;
    private double marks12th;
    private double diplomaMarks;
    private double graduationMarks;
    private String domain;
    private int myPerfecticeLevel;
    private double IELTSScore;
    private double coadingRating;
    private String technicalSkills;
    private StudentChoice studentChoice;
    private String cvLink;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
//    private Role userRole;
    @Enumerated(EnumType.ORDINAL)
    private RoleType role;

}
