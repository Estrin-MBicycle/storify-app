package internship.mbicycle.storify.controllers;

import internship.mbicycle.storify.jpa.StorifyUserRepository;
import internship.mbicycle.storify.models.StorifyUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(value = "storify user controller")
public class UserController {

    private final StorifyUserRepository userRepository;

    @ApiOperation(value = "get user by id")
    @GetMapping("/{id}")
    public StorifyUser getUser(@PathVariable(name = "id") long id) {
        return userRepository.findById(id);
    }

    @ApiOperation(value = "get all users")
    @GetMapping("/all")
    public List<StorifyUser> getAll() {
        return userRepository.findAll();
    }

}
