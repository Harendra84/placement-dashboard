package in.org.gramtarang.placementdashboard.service;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ResponseEntityDto<User> response = new ResponseEntityDto<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateLogin(String username, String password) throws PlacementException{

        User user = this.userRepository.findUserByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword()))
                return user;
            else
                throw new PlacementException("Incorrect User or Password");
        } else {
            throw new PlacementException("Invalid User or Password");
        }
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public ResponseEntityDto<User> updateUser(User user) throws PlacementException{
       Optional<User> updatedUser = userRepository.findById(user.getUserId());
       if(updatedUser.isPresent())
       {
           response.setMessage("User Updated Successfully");
           response.setStatus(true);
           response.setData(userRepository.save(user));
       }
       response.setMessage("User doesn't exist");
       response.setStatus(false);
       return response;
    }
}
