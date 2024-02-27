package passoffTests.serviceTests;

import model.UserRequests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserService;


public class UserServiceTests {
    @Test
    void registerUser() {
        var userService = new UserService();
        var req = new UserRequests("joe", "pw", "joe@joe.com");

        var res = Assertions.assertDoesNotThrow(() -> userService.registerUser(req));

        Assertions.assertEquals("joe", res.getUsername());
        //dont have to check for empty strings already checks in TA tests
    }
}
