package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.common.StudentPlacementStatus;
import in.org.gramtarang.placementdashboard.entity.Group;
import in.org.gramtarang.placementdashboard.entity.PlacementData;
import in.org.gramtarang.placementdashboard.entity.Recruitment;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.GroupRepository;
import in.org.gramtarang.placementdashboard.repository.PlacementDataRepository;
import in.org.gramtarang.placementdashboard.repository.RecruitmentRepository;
import in.org.gramtarang.placementdashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlacementDataService {

    private final PlacementDataRepository placementDataRepository;

    private final UserRepository userRepository;

    private final RecruitmentRepository recruitmentRepository;
    ResponseEntityDto<PlacementData> response = new ResponseEntityDto<>();

    public PlacementDataService(PlacementDataRepository placementDataRepository, UserRepository userRepository, RecruitmentRepository recruitmentRepository) {
        this.placementDataRepository = placementDataRepository;
        this.userRepository = userRepository;
        this.recruitmentRepository = recruitmentRepository;
    }


    public ResponseEntityDto<PlacementData> addPlacementData(String studentPlacementStatus, String remarks, String feedbackFromRecruiters, int studentId, int recruitmentId, int userId) throws PlacementException {
        
        try{
            PlacementData placementData = new PlacementData();
            Optional<Recruitment> recruitment = recruitmentRepository.findById(recruitmentId);
            Optional<User> user = userRepository.findById(userId);
            Optional<User> student = userRepository.findById(studentId);
            List<PlacementData> placementDataList = placementDataRepository.findAll();
            if(placementDataExists(placementDataList, recruitment, student))
            {
                response.setMessage("Student already Placed Or Student Doesn't Exist");
                response.setStatus(false);
            } else{
                if(student.isPresent() && student.get().getRole().toString() == "STUDENT"){
                    placementData.setStudentPlacementStatus(StudentPlacementStatus.valueOf(studentPlacementStatus));
                    placementData.setUser(student.get());
                    if(studentPlacementStatus == "REJECTED"){
                        placementData.setRemarks(remarks);
                    }
                    if(recruitment.isPresent()){
                        placementData.setRecruitment(recruitment.get());
                    } else{
                        response.setMessage("Recruitment doesn't exist");
                        response.setStatus(false);
                    }
                    placementData.setFeedbackFromRecruiters(feedbackFromRecruiters);
                    response.setData(placementDataRepository.save(placementData));
                    response.setMessage("PlacementData Added SuccessFully");
                    response.setStatus(true);
                }
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Adding PlacementData");
            throw new PlacementException("Exception while Adding PlacementData");
        }
        return response;
    }

    private boolean placementDataExists(List<PlacementData> placementDataList, Optional<Recruitment> recruitment, Optional<User> student) {

        if(recruitment.isPresent() && student.isPresent())
        {
          int studentId = student.get().getUserId();
          int recruitmentId = recruitment.get().getRecruitmentId();

            for(PlacementData placementData : placementDataList){
                if(placementData.getUser().getUserId() == studentId && placementData.getRecruitment().getRecruitmentId() == recruitmentId)
                {
                    return true;
                } else{
                    return false;
                }
            }
        }
        return true;
    }

    public ResponseEntityDto<PlacementData> updatePlacementData(int placementId, String studentPlacementStatus, String remarks, String feedbackFromRecruiters, int studentId, int recruitmentId, int userId) throws PlacementException{
         
        Optional<PlacementData> updatedPlacementData = placementDataRepository.findById(placementId);
        if(updatedPlacementData.isPresent())
        {
            Optional<Recruitment> recruitment = recruitmentRepository.findById(recruitmentId);
            Optional<User> user = userRepository.findById(userId);
            Optional<User> student = userRepository.findById(studentId);
            if(student.isPresent() && student.get().getRole().toString() == "STUDENT"){
                updatedPlacementData.get().setStudentPlacementStatus(StudentPlacementStatus.valueOf(studentPlacementStatus));
                updatedPlacementData.get().setUser(student.get());
                if(studentPlacementStatus == "REJECTED"){
                    updatedPlacementData.get().setRemarks(remarks);
                }
                if(recruitment.isPresent()){
                    updatedPlacementData.get().setRecruitment(recruitment.get());
                } else{
                    throw new PlacementException("Recruitment doesn't exist");
                }
                updatedPlacementData.get().setFeedbackFromRecruiters(feedbackFromRecruiters);
                response.setData(placementDataRepository.save(updatedPlacementData.get()));
                response.setMessage("PlacementData Updated Successfully");
                response.setStatus(true);
            }
        } else{
            response.setMessage("PlacementData doesn't exist");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseEntityDto<PlacementData> listPlacementData() throws PlacementException{
         
        try{
            List<PlacementData> placementDatas = placementDataRepository.findAll();
            response.setListOfData(placementDatas);
            response.setMessage("PlacementData listed SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Listing PlacementData");
            throw new PlacementException("Exception while listing PlacementData");
        }
        return response;
    }

    public ResponseEntityDto<PlacementData> deletePlacementData(int placementDataId) throws PlacementException{
         
        try{
            placementDataRepository.deleteById(placementDataId);
            response.setMessage("PlacementData Deleted SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMessage("PlacementData Doesn't Exist");
            response.setStatus(false);
            throw new PlacementException("PlacementData Doesn't Exist");
        }
        return response;
    }

}
