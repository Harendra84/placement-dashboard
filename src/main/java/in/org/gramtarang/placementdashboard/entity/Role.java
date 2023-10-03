package in.org.gramtarang.placementdashboard.entity;

import in.org.gramtarang.placementdashboard.common.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String roleName;

    @Enumerated(EnumType.ORDINAL)
    private RoleType roleType;

}
