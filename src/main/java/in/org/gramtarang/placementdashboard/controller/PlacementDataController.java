package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.common.StudentPlacementStatus;
import in.org.gramtarang.placementdashboard.entity.PlacementData;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.PlacementDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/placementData")
public class PlacementDataController {
    private final PlacementDataService placementDataService;

    public PlacementDataController(PlacementDataService placementDataService) {
        this.placementDataService = placementDataService;
    }
    ResponseEntityDto<PlacementData> response = new ResponseEntityDto<>();

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<PlacementData>> addPlacementData(@RequestParam StudentPlacementStatus studentPlacementStatus,
                                                                     @RequestParam String remarks,
                                                                     @RequestParam String feedbackFromRecruiters,
                                                                     @RequestParam int userId,
                                                                     @RequestParam int recruitmentId,
                                                                     HttpServletRequest request) throws PlacementException {
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            String loginUserId = request.getSession().getAttribute("USERID").toString();
            response = placementDataService.addPlacementData(studentPlacementStatus.toString(), remarks, feedbackFromRecruiters, userId, recruitmentId, Integer.parseInt(loginUserId));
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<PlacementData>> updatePlacementData(@RequestParam StudentPlacementStatus studentPlacementStatus,
                                                                                @RequestParam String remarks,
                                                                                @RequestParam String feedbackFromRecruiters,
                                                                                @RequestParam int userId,
                                                                                @RequestParam int recruitmentId,
                                                                        @RequestParam int placementId,
                                                                        HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            String loginUserId = request.getSession().getAttribute("USERID").toString();
            response = placementDataService.updatePlacementData(placementId, studentPlacementStatus.toString(), remarks, feedbackFromRecruiters, userId, recruitmentId, Integer.parseInt(loginUserId));

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseEntityDto<PlacementData>> listPlacementData(HttpServletRequest request) throws PlacementException{
        response = null;
        response = placementDataService.listPlacementData();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{placementDataId}")
    public ResponseEntity<ResponseEntityDto<PlacementData>> deletePlacementData(@PathVariable(value = "placementDataId") int placementDataId, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = placementDataService.deletePlacementData(placementDataId);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
