package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Company;
import in.org.gramtarang.placementdashboard.entity.Recruitment;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.CompanyRepository;
import in.org.gramtarang.placementdashboard.repository.RecruitmentRepository;
import in.org.gramtarang.placementdashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    ResponseEntityDto<Recruitment> response = new ResponseEntityDto<>();

    public RecruitmentService(RecruitmentRepository recruitmentRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        this.recruitmentRepository = recruitmentRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }


    public ResponseEntityDto<Recruitment> addRecruitment(Recruitment recruitment, int userId) throws PlacementException {
         
        try{
            Optional<User> user = userRepository.findById(userId);
            Optional<Company> company = companyRepository.findById(recruitment.getCompany().getCompanyId());
            if(user.isPresent() && user.get().getRole().toString() == "PURSUIT_MANAGER"){
                recruitment.setPursuitManager(user.get().getFullName());
                recruitment.setCompany(company.get());
                response.setData(recruitmentRepository.save(recruitment));
                response.setMessage("Recruitment Data Added SuccessFully");
                response.setStatus(true);
            } else{
                response.setStatus(false);
                response.setMessage("Invalid User Role");
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Invalid User Role");
            throw new PlacementException("Exception while Adding Recruitment");
        }
        return response;
    }

    public ResponseEntityDto<Recruitment> updateRecruitment(Recruitment recruitment, int userId) throws PlacementException{
         
        Optional<Recruitment> updatedRecruitment = recruitmentRepository.findById(recruitment.getRecruitmentId());
        if(updatedRecruitment.isPresent())
        {
            Optional<User> user = userRepository.findById(userId);
            Optional<Company> company = companyRepository.findById(recruitment.getCompany().getCompanyId());
            if(user.isPresent() && user.get().getRole().toString() == "PURSUIT_MANAGER"){
                recruitment.setPursuitManager(user.get().getFullName());
                recruitment.setCompany(company.get());
                response.setData(recruitmentRepository.save(recruitment));
                response.setMessage("Recruitment Data Updated Successfully");
                response.setStatus(true);
            }
        } else{
            response.setMessage("Recruitment doesn't exist");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseEntityDto<Recruitment> listRecruitment() throws PlacementException{
         
        try{
            List<Recruitment> recruitments = recruitmentRepository.findAll();
            response.setListOfData(recruitments);
            response.setMessage("Recruitment listed SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Listing Recruitment");
            throw new PlacementException("Exception while listing Recruitment");
        }
        return response;
    }

    public ResponseEntityDto<Recruitment> deleteRecruitment(int recruitmentId) throws PlacementException{
         
        try{
            recruitmentRepository.deleteById(recruitmentId);
            response.setMessage("Recruitment Deleted SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMessage("Recruitment Doesn't Exist");
            response.setStatus(false);
            throw new PlacementException("Recruitment Doesn't Exist");
        }
        return response;
    }
}
