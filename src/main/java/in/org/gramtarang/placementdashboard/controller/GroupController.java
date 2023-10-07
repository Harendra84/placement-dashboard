package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Group;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.GroupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    ResponseEntityDto<Group> response = new ResponseEntityDto<>();

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<Group>> addGroup(@RequestBody Group group, HttpServletRequest request) throws PlacementException {
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = groupService.addGroup(group);
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<Group>> updateGroup(@RequestBody Group group, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = groupService.updateGroup(group);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseEntityDto<Group>> listGroup(HttpServletRequest request) throws PlacementException{
        response = null;
        response = groupService.listGroup();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{groupId}")
    public ResponseEntity<ResponseEntityDto<Group>> deleteGroup(@PathVariable(value = "groupId") int groupId, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = groupService.deleteGroup(groupId);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
