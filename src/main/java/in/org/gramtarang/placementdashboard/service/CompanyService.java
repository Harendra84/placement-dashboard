package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.Company;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    ResponseEntityDto<Company> response = new ResponseEntityDto<>();

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResponseEntityDto<Company> addCompany(Company company) throws PlacementException {
        
        try{
           Company company1 = companyRepository.save(company);
           response.setData(company1);
           response.setMessage("Company Added SuccessFully");
           response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Adding Company");
            throw new PlacementException("Exception while Adding Company");
        }
        return response;
    }

    public ResponseEntityDto<Company> updateCompany(Company company) throws PlacementException{
        
        Optional<Company> updatedCompany = companyRepository.findById(company.getCompanyId());
        if(updatedCompany.isPresent())
        {
            response.setMessage("Company Updated Successfully");
            response.setStatus(true);
            response.setData(companyRepository.save(company));
        } else{
            response.setMessage("Company doesn't exist");
            response.setStatus(false);
        }
        return response;
    }

    public ResponseEntityDto<Company> listCompany() throws PlacementException{

        
        try{
            List<Company> companies = companyRepository.findAll();
            response.setListOfData(companies);
            response.setMessage("Company listed SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error While Listing Company");
            throw new PlacementException("Exception while listing Company");
        }
        return response;
    }


    public ResponseEntityDto<Company> deleteCompany(int companyId) throws PlacementException{

        
        try{
            companyRepository.deleteById(companyId);
            response.setMessage("Company Deleted SuccessFully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMessage("Company Doesn't Exist");
            response.setStatus(false);
            throw new PlacementException("Company Doesn't Exist");
        }
        return response;
    }
}
