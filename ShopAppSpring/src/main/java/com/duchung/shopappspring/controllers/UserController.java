package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.dtos.UserUpdateDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.exceptions.UsernameOrPasswordIsWrong;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.responses.*;
import com.duchung.shopappspring.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/staffs")
    public ResponseEntity<?> getAllStaffs(@RequestParam(defaultValue = "", value = "keyword") String keyword,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit) {
        var staffs = userService.getAllStaffs(keyword,
                PageRequest.of(page, limit, Sort.by("id").ascending()));
        int totalPage = staffs.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Staffs page is empty!"));
        }
        if (staffs.getContent().isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Staffs page is empty!"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(UserListResponse.builder()
                .users(staffs)
                .totalPage(totalPage)
                .build(),
                "Get all staffs"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO,
                                      BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result. getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new ErrorResponse<>(errorMessages));
        }
        try {
            return ResponseEntity.ok().body(new SuccessResponse<>(userService.createUser(userDTO),
                    "Register successfully !"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PostMapping ("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                        BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new ErrorResponse<>(errorMessages));
        }
        try {
            return ResponseEntity.ok().body(AuthenticationResponse.builder()
                            .time(LocalDateTime.now())
                            .message("Login successfully!")
                            .token(userService.login(userLoginDTO))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PostMapping("/details")
    public ResponseEntity<?> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
        User user = userService.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok().body(
                new SuccessResponse<>(UserResponse.fromUser(user), "Get user details")
        );
    }

    @GetMapping("/avatar/{avatarName}")
    public ResponseEntity<?> viewImage(@PathVariable String avatarName) {
        Path imagePath = Paths.get("uploads/avatars/" + avatarName);
        try {
            UrlResource urlResource = new UrlResource(imagePath.toUri());
            if (urlResource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(urlResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new SuccessResponse<>("Deleted!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(userService.getUser(userId),
                    "Get user"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            userService.updateUser(userId, userUpdateDTO);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>("Update successfully!"));
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long userId,
                                          @ModelAttribute("file") MultipartFile file) {
        try {
            userService.uploadAvatar(userId, file);
            return ResponseEntity.ok().body(new SuccessResponse<>("Uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/count/customers")
    public ResponseEntity<?> countCustomers() {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(userService.countCustomers()));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/count/staffs")
    public ResponseEntity<?> countStaffs() {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(userService.countStaffs()));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/count/get-latest-customers")
    public ResponseEntity<?> getLatestCustomer() {
        return ResponseEntity.ok(new SuccessResponse<>(userService.getLatestCustomers()));
    }

}


