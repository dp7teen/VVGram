package com.dp.vvgram.controllers;

import com.dp.vvgram.dtos.*;
import com.dp.vvgram.exceptions.*;
import com.dp.vvgram.models.User;
import com.dp.vvgram.services.UserService;
import com.dp.vvgram.utilities.PrincipalHelper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User can signup here!  No restrictions implemented.")
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto requestDto) throws UserAlreadyExistsException {
        User user = userService.signUp(
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getEmail()
        );
        return UserDto.from(user);
    }

    @Operation(summary = "User can login here!  Use the correct email and password while you signed up.")
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundException,
            InvalidPasswordException {
        String token = userService.login(
                requestDto.getUsername(),
                requestDto.getPassword()
        );
        return TokenResponseDto.from(token);
    }

    @Operation(summary = "User can view their profile data.")
    @GetMapping("/user") //todo: check here itself.  by sending request.
    public UserDto getUserProfile() throws UserNotFoundException {
        UserDetails details = PrincipalHelper.getPrincipal();
        User user = userService.getUserProfile(details.getUsername());
        return UserDto.from(user);
    }

    @Operation(summary = "User can search for Users with some username!")
    @PostMapping
    public ResponseEntity<Page<UserDto>> getUserProfiles(@RequestBody SearchUserDto dto) {
        Page<UserDto> users = userService.getUsers(dto.getUsername(), dto.getPageno(),
                dto.getPagesize(),dto.getSortBy());
        return new ResponseEntity<>(
                users, HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() throws AccessDeniedException {
        String message = userService.logout();
        return new ResponseEntity<>(
                message,
                HttpStatus.OK
        );
    }

    @Operation(summary = "User can update their profile.  Enter only required fields remove the unnecessary fields before executing!")
    @PatchMapping("/update")
    public UserDto updateProfile(@RequestBody UpdateProfileDto updateProfileDto) throws UserNotFoundException{
        UserDetails details = PrincipalHelper.getPrincipal();
        User user = userService.updateProfile(details.getUsername(), updateProfileDto);
        return UserDto.from(user);
    }
}
