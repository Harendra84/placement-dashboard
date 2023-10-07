package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Group;
import in.org.gramtarang.placementdashboard.entity.TimeSheet;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.GroupRepository;
import in.org.gramtarang.placementdashboard.repository.TimeSheetRepository;
import in.org.gramtarang.placementdashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;
    ResponseEntityDto<TimeSheet> response = new ResponseEntityDto<>();

    public TimeSheetService(TimeSheetRepository timeSheetRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }


    public ResponseEntityDto<TimeSheet> addTimeSheet(TimeSheet timeSheet, int userId) throws PlacementException {
        
        try{
//            TimeSheet timeSheet = new TimeSheet();
            Optional<Group> group = groupRepository.findById(timeSheet.getGroup().getGroupId());
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent() && user.get().getRole().toString() == "TRAINER"){
                timeSheet.setDatetime(timeSheet.getDatetime());
                timeSheet.setUser(user.get());
                timeSheet.setGroup(group.get());
                response.setData(timeSheetRepository.save(timeSheet));
                response.setMessage("TimeSheet Added SuccessFully");
                response.setStatus(true);
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Adding TimeSheet");
            throw new PlacementException("Exception while Adding TimeSheet");
        }
        return response;
    }

    public ResponseEntityDto<TimeSheet> updateTimeSheet(TimeSheet timeSheet, int userId) throws PlacementException{
         
        Optional<TimeSheet> updatedTimeSheet = timeSheetRepository.findById(timeSheet.getTimesheetId());
        if(updatedTimeSheet.isPresent())
        {
//            TimeSheet timeSheet = new TimeSheet();
            Optional<Group> group = groupRepository.findById(timeSheet.getGroup().getGroupId());
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent() && user.get().getRole().toString() == "TRAINER"){
                timeSheet.setDatetime(timeSheet.getDatetime());
                timeSheet.setUser(user.get());
                timeSheet.setGroup(group.get());
                response.setData(timeSheetRepository.save(timeSheet));
                response.setMessage("TimeSheet Updated Successfully");
                response.setStatus(true);
            }
        } else{
            response.setMessage("TimeSheet doesn't exist");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseEntityDto<TimeSheet> listTimeSheet() throws PlacementException{
        
        try{
            List<TimeSheet> timeSheets = timeSheetRepository.findAll();
            response.setListOfData(timeSheets);
            response.setMessage("TimeSheet listed SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Listing TimeSheet");
            throw new PlacementException("Exception while listing TimeSheet");
        }
        return response;
    }

    public ResponseEntityDto<TimeSheet> deleteTimeSheet(int timeSheetId) throws PlacementException{
        
        try{
            timeSheetRepository.deleteById(timeSheetId);
            response.setMessage("TimeSheet Deleted SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMessage("TimeSheet Doesn't Exist");
            response.setStatus(false);
            throw new PlacementException("TimeSheet Doesn't Exist");
        }
        return response;
    }
}
