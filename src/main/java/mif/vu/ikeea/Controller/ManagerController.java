package mif.vu.ikeea.Controller;

import mif.vu.ikeea.Entity.ApplicationUser;
import mif.vu.ikeea.Exceptions.InvalidUserRoleException;
import mif.vu.ikeea.Factory.UserManagerResponseFactory;
import mif.vu.ikeea.Manager.UserManager;
import mif.vu.ikeea.RepositoryService.UserService;
import mif.vu.ikeea.Responses.UserManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @Autowired
    private UserManager userManager;

    @GetMapping(path = "/{id}/users")
    public @ResponseBody
    List<UserManagerResponse> list(@PathVariable Long id) {

        ApplicationUser manager = userService.loadById(id);

        if(!userManager.checkIfValidRole(manager))
            throw new InvalidUserRoleException("Invalid user role");

        List<ApplicationUser> childUsers = manager.getChildren();
        List<UserManagerResponse> userManagerResponses = new ArrayList<>();

        for (ApplicationUser user : childUsers) {
            UserManagerResponse response = UserManagerResponseFactory.create(user, user.getChildren());
            userManagerResponses.add(response);
        }

        return userManagerResponses;
    }
}
