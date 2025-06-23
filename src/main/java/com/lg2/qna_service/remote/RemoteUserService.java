package com.lg2.qna_service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", path = "/api/auth/users")
public interface RemoteUserService {
    @GetMapping("/me")
    UserDto getUser(@RequestHeader("Authorization") String token);


}
