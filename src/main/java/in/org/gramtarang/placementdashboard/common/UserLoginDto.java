package in.org.gramtarang.placementdashboard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {
    private String fullName;
    private String username;
    private RoleType roleType;
}
