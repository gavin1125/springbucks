package cn.gavin.springbucks.web;

import cn.gavin.springbucks.domain.RegisterUseCase;
import cn.gavin.springbucks.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterRestController {
    private final RegisterUseCase registerUseCase;

    @PostMapping("/forums/{forumId}/register")
    UserResource register(
            @PathVariable("forumId") Long forumId,
            @Valid @RequestBody UserResource userResource,
            @RequestParam("sendWelcomeMail") boolean sendWelcomeMail
    ) {
        User user = new User(userResource.getName(),
                userResource.getMail());

        Long userId = registerUseCase.registerUser(user, sendWelcomeMail);

        return new UserResource(user.getName(), user.getEmail());
    }


}
