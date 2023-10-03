package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
}
