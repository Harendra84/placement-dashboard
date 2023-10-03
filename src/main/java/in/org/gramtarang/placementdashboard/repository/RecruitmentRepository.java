package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
}
