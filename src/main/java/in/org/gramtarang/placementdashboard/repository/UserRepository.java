package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.common.RoleType;
import in.org.gramtarang.placementdashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    List<User> findUsersByRole(RoleType roleName);
    User findUserByRegistrationNo(String registrationNo);
}
