package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Recruitment;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.RecruitmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }
    ResponseEntityDto<Recruitment> response = new ResponseEntityDto<>();

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<Recruitment>> addRecruitment(@RequestBody Recruitment recruitment,
                                                                     HttpServletRequest request) throws PlacementException {
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            String userId = request.getSession().getAttribute("USERID").toString();
            response = recruitmentService.addRecruitment(recruitment, Integer.parseInt(userId));
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<Recruitment>> updateRecruitment(@RequestBody Recruitment recruitment,
                                                                        HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            String userId = request.getSession().getAttribute("USERID").toString();
            response = recruitmentService.updateRecruitment(recruitment, Integer.parseInt(userId));

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseEntityDto<Recruitment>> listRecruitment(HttpServletRequest request) throws PlacementException{
        response = null;
        response = recruitmentService.listRecruitment();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{recruitmentId}")
    public ResponseEntity<ResponseEntityDto<Recruitment>> deleteRecruitment(@PathVariable(value = "recruitmentId") int recruitmentId, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = recruitmentService.deleteRecruitment(recruitmentId);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
