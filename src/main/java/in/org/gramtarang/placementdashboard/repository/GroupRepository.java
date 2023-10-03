package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
