package in.org.gramtarang.placementdashboard.entity;

import in.org.gramtarang.placementdashboard.common.StudentPlacementStatus;
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
public class PlacementData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placementId;
    private StudentPlacementStatus studentPlacementStatus;
    private String remarks;
    private String feedbackFromRecruiters; //for that we will work

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private  User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

}
