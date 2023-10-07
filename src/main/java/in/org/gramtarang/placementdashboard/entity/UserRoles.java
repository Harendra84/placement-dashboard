//package in.org.gramtarang.placementdashboard.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Entity
//public class UserRoles {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int userRoleId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//}
