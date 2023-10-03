package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.entity.PlacementData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementDataRepository extends JpaRepository<PlacementData, Integer> {

}
