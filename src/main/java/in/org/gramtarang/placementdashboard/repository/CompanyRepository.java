package in.org.gramtarang.placementdashboard.repository;

import in.org.gramtarang.placementdashboard.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
