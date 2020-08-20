package engine.users;

import engine.exceptions.UsernameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(path = "/api/register")
    public void registration(@Valid @RequestBody MyUser user) {
        if (users.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyTakenException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
    }

}
