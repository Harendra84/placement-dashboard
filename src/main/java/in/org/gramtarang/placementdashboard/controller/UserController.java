package in.org.gramtarang.placementdashboard.controller;

import in.org.gramtarang.placementdashboard.common.ResponseEntityDto;
import in.org.gramtarang.placementdashboard.common.RoleType;
import in.org.gramtarang.placementdashboard.common.UserLoginDto;
import in.org.gramtarang.placementdashboard.entity.User;
import in.org.gramtarang.placementdashboard.exception.PlacementException;
import in.org.gramtarang.placementdashboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    ResponseEntityDto<User> response = new ResponseEntityDto<>();

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestParam String username, @RequestParam String password,
                                              HttpServletRequest request) throws PlacementException
    {
        User user = userService.authenticateLogin(username, password);
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        if (user.getUserRole() != null) {
            dto.setRoleType(user.getUserRole().getRoleType());
        } else {
            dto.setRoleType(RoleType.UNDEFINED);
        }
        request.getSession().setAttribute("USERID", user.getUserId());
        request.getSession().setAttribute("ROLE-TYPE", user.getUserRole().getRoleType());
        request.getSession().setAttribute("USERNAME",user.getUsername());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<ResponseEntityDto<User>> createUser(@RequestBody User user, HttpServletRequest request) throws PlacementException{
        if(request.getSession().getAttribute("ROLE-TYPE").toString() == "ADMIN")
        {
            User createdUser = userService.addUser(user);
            response.setData(createdUser);
            response.setMessage("User Added Successfully");
            response.setStatus(true);
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update-user")
    public ResponseEntity<ResponseEntityDto<User>> updateUser(@RequestBody User user, HttpServletRequest request) throws PlacementException{
        if(request.getSession().getAttribute("ROLE-TYPE").toString() == "ADMIN")
        {
            response = userService.updateUser(user);

        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public @ResponseBody
    UserLoginDto logout(HttpServletRequest request) throws PlacementException {
        request.getSession().invalidate();
        request.getSession().removeAttribute("USERID");
        request.getSession().removeAttribute("ROLE-TYPE");
        request.getSession().removeAttribute("USERNAME");
        return new UserLoginDto();
    }

//    public ResponseEntity<ResponseEntityDto<UserInfoDto>>

}
