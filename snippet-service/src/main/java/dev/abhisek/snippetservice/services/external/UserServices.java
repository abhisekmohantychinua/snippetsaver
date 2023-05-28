package dev.abhisek.snippetservice.services.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServices {
    @GetMapping("/user/verify/{userId}")
    Boolean verifyUser(@PathVariable String  userId);
}
