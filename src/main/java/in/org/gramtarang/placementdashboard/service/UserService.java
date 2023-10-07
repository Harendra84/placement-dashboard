package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.common.RoleType;
import in.org.gramtarang.placementdashboard.common.UserInfoDto;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ResponseEntityDto<User> response = new ResponseEntityDto<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntityDto<User> authenticateLogin(String username, String password) throws PlacementException{

        
        User user = this.userRepository.findUserByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())){
                response.setStatus(true);
                response.setMessage("User Logged In Successfully");
                response.setData(user);
            }
            else {
                response.setStatus(false);
                response.setMessage("Incorrect User or Password");
            }
        } else {
            response.setStatus(false);
            response.setMessage("Incorrect User or Password");
            throw new PlacementException("Invalid User or Password");
        }
        return response;
    }

    public ResponseEntityDto<User> addUser(User user) throws PlacementException{
         
        try{
            User isUser;
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getRegistrationNo() == null)
            {
                isUser = userRepository.findUserByUsername(user.getUsername());
            } else{
                isUser = userRepository.findUserByRegistrationNo(user.getRegistrationNo());
            }
            if(isUser != null)
            {
                response.setStatus(false);
                response.setMessage("User Already Exists");
                return response;
            }
            response.setMessage("User Added Successfully");
            response.setStatus(true);
            response.setData(userRepository.save(user));
        } catch (Exception e) {
            throw new PlacementException("User Already Exists");
        }
        return response;
    }

    public ResponseEntityDto<User> updateUser(User user) throws PlacementException{
       
       Optional<User> updatedUser = userRepository.findById(user.getUserId());
       if(updatedUser.isPresent())
       {
           response.setMessage("User Updated Successfully");
           response.setStatus(true);
           response.setData(userRepository.save(user));
       } else {
           response.setMessage("User doesn't exist");
           response.setStatus(false);
       }
       return response;
    }

    public ResponseEntityDto<User> getUserByRole(String roleName) throws PlacementException{
         
        try{
            List<User> users = userRepository.findUsersByRole(RoleType.valueOf(roleName));
            response.setStatus(true);
            response.setMessage("User List Succussfully");
            response.setListOfData(users);
        } catch (PlacementException pe){
            throw new PlacementException("Invalid RoleType");
        }
        return response;
    }

    public ResponseEntityDto<User> getStudentDetails(int userId) throws PlacementException{
        
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            response.setStatus(true);
            response.setMessage("User Details");
            response.setData(user.get());
        }else{
            response.setMessage("User Doesn't Exist");
            response.setStatus(false);
        }
        return response;
    }
}
