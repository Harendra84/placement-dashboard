package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Group;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    ResponseEntityDto<Group> response = new ResponseEntityDto<>();

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public ResponseEntityDto<Group> addGroup(Group group) throws PlacementException {
         
        try{
            Group group1 = groupRepository.save(group);
            response.setData(group1);
            response.setMessage("Group Added SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Adding Group");
            throw new PlacementException("Exception while Adding Group");
        }
        return response;
    }

    public ResponseEntityDto<Group> updateGroup(Group group) throws PlacementException{
         
        Optional<Group> updatedGroup = groupRepository.findById(group.getGroupId());
        if(updatedGroup.isPresent())
        {
            response.setMessage("Group Updated Successfully");
            response.setStatus(true);
            response.setData(groupRepository.save(group));
        } else{
            response.setMessage("Group doesn't exist");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseEntityDto<Group> listGroup() throws PlacementException{
         
        try{
            List<Group> groups = groupRepository.findAll();
            response.setListOfData(groups);
            response.setMessage("Group listed SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Listing Group");
            throw new PlacementException("Exception while listing Group");
        }
        return response;
    }

    public ResponseEntityDto<Group> deleteGroup(int groupId) throws PlacementException{
         
        try{
            groupRepository.deleteById(groupId);
            response.setMessage("Group Deleted SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMessage("Group Doesn't Exist");
            response.setStatus(false);
            throw new PlacementException("Group Doesn't Exist");
        }
        return response;
    }
}
