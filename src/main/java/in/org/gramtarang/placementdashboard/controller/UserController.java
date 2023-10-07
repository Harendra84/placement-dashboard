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
    ResponseEntityDto<UserLoginDto> loginDtoResponse = new ResponseEntityDto<>();

    @PostMapping("/login")
    public ResponseEntity<ResponseEntityDto<UserLoginDto>> login(@RequestParam String username, @RequestParam String password,
                                              HttpServletRequest request) throws PlacementException
    {

        UserLoginDto dto = new UserLoginDto();
        response = userService.authenticateLogin(username, password);
        if(response.isStatus())
        {
            User user = response.getData();
            dto.setUsername(user.getUsername());
            dto.setFullName(user.getFullName());
            if (user.getRole() != null) {
                dto.setRoleType(user.getRole());
            } else {
                dto.setRoleType(RoleType.UNDEFINED);
            }
            loginDtoResponse.setMessage("Login SuccessFully");
            loginDtoResponse.setStatus(true);
            loginDtoResponse.setData(dto);
            request.getSession().setAttribute("USERID", user.getUserId());
            request.getSession().setAttribute("ROLE-TYPE", user.getRole());
            request.getSession().setAttribute("USERNAME",user.getUsername());
        } else{
            loginDtoResponse.setMessage("Invalid UserName and Password");
            loginDtoResponse.setStatus(false);
        }
        return new ResponseEntity<>(loginDtoResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseEntityDto<User>> createUser(@RequestBody User user, HttpServletRequest request) throws PlacementException{
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() == "ADMIN")
        {
            response = userService.addUser(user);
        } else{
            response.setMessage("Invalid User");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseEntityDto<User>> updateUser(@RequestBody User user, HttpServletRequest request) throws PlacementException{
        response = null;
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
        response = null;
        request.getSession().invalidate();
        request.getSession().removeAttribute("USERID");
        request.getSession().removeAttribute("ROLE-TYPE");
        request.getSession().removeAttribute("USERNAME");
        return new UserLoginDto();
    }
    @PostMapping("/listBy")
    public ResponseEntity<ResponseEntityDto<User>> getUserByRole(@RequestParam String roleName, HttpServletRequest request){
        //first get the role id by roletype
        //then list the users details by using roleid from the userroles table
        response = null;
        if(request.getSession().getAttribute("ROLE-TYPE").toString() != "STUDENT"){
            response = userService.getUserByRole(roleName);
        } else{
            response.setMessage("Invalid UserRole");
            response.setStatus(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/listStudentDetails/{userId}")
    public ResponseEntity<ResponseEntityDto<User>> getStudentDetails(@PathVariable(value = "userId") int userId, HttpServletRequest request) throws PlacementException{
        response = null;
        response = userService.getStudentDetails(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
