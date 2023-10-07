package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Company;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    ResponseEntityDto<Company> response = new ResponseEntityDto<>();

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<Company>> addCompany(@RequestBody Company company, HttpServletRequest request) throws PlacementException {
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
           response = companyService.addCompany(company);
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<Company>> updateCompany(@RequestBody Company company, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = companyService.updateCompany(company);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseEntityDto<Company>> listCompany(HttpServletRequest request) throws PlacementException{
        response = null;
        response = companyService.listCompany();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{companyId}")
    public ResponseEntity<ResponseEntityDto<Company>> deleteCompany(@PathVariable(value = "companyId") int companyId, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT")
        {
            response = companyService.deleteCompany(companyId);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
