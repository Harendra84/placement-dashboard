package in.org.gramtarang.placementdashboard.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {
    private boolean isActive;
    private String emailId;
    private String fullName;
    private String phoneNo;
    private String gender;
    private String campus;
    private String school;
    private String branch;
}
