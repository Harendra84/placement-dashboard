package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.TimeSheet;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.TimeSheetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {

    private final TimeSheetService timeSheetService;

    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }
    ResponseEntityDto<TimeSheet> response = new ResponseEntityDto<>();

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<TimeSheet>> addTimeSheet(@RequestBody TimeSheet timeSheet,
                                                                     HttpServletRequest request) throws PlacementException {
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
             String userId = request.getSession().getAttribute("USERID").toString();
             response = timeSheetService.addTimeSheet(timeSheet, Integer.parseInt(userId));
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<TimeSheet>> updateTimeSheet(@RequestBody TimeSheet timeSheet,
                                                                        HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            String userId = request.getSession().getAttribute("USERID").toString();
            response = timeSheetService.updateTimeSheet(timeSheet, Integer.parseInt(userId));

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseEntityDto<TimeSheet>> listTimeSheet(HttpServletRequest request) throws PlacementException{
        response = null;
        response = timeSheetService.listTimeSheet();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{timeSheetId}")
    public ResponseEntity<ResponseEntityDto<TimeSheet>> deleteTimeSheet(@PathVariable(value = "timeSheetId") int timeSheetId, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = timeSheetService.deleteTimeSheet(timeSheetId);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
