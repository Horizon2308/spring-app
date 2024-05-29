package com.duchung.shopappspring.services;

import com.duchung.shopappspring.components.JwtTokenUtils;
import com.duchung.shopappspring.domains.IsActive;
import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.dtos.UserUpdateDTO;
import com.duchung.shopappspring.exceptions.*;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.repositories.RoleRepository;
import com.duchung.shopappspring.repositories.UserRepository;
import com.duchung.shopappspring.responses.CustomerStatisticResponse;
import com.duchung.shopappspring.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException, DataExistedException, InvalidParameterException {
        if (userRepository.existsUserByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataExistedException("Phone number is existed");
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            throw new InvalidParameterException("Password and retype password don't match");
        }
        User newUser = User.builder()
                .dateOfBirth(userDTO.getDateOfBirth())
                .fullName(userDTO.getFullName())
                .address(userDTO.getAddress())
                .googleAccountId(userDTO.getFacebookAccountId())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .isActive(IsActive.ENABLE)
                .cic(userDTO.getCic())
                .email(userDTO.getEmail())
                .sex(userDTO.getSex())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role doesn't exist!"));

        newUser.setRole(role);
        // If user register by facebook account or google account, we don't need password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws InvalidParameterException, UsernameOrPasswordIsWrong {
        Role role = roleRepository.findById(userLoginDTO.getRoleId())
                .orElseThrow(() -> new InvalidParameterException("Invalid role"));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(),
                userLoginDTO.getPassword(),
                null
        );
        var isAuthentication = authenticationManager.authenticate(authenticationToken);
        if (isAuthentication.isAuthenticated()) {
            String token = jwtTokenUtils.generateToken((User) isAuthentication.getPrincipal());
            assert token != null;
            return token;
        } else {
            throw new UsernameOrPasswordIsWrong("Username or password is wrong!");
        }
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String phoneNumber = jwtTokenUtils.exactPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public Page<UserResponse> getAllStaffs(String keyword, Pageable pageable) {
        return userRepository.searchStaffs(keyword, pageable).map(UserResponse::fromUser);
    }

    @Override
    public void uploadAvatar(Long userId, MultipartFile file) throws DataNotFoundException,
            InvalidParameterException,
            IOException {
        User uploadedUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        uploadedUser.setAvatar(storeFile(handleMultipartFile(file)));
        userRepository.save(uploadedUser);
    }

    private MultipartFile handleMultipartFile(MultipartFile file) throws InvalidParameterException {
        if (file == null || file.getSize() == 0) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is invalid image");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is too large! Maximum size is 10MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidParameterException(file.getOriginalFilename() + " is invalid image");
        }
        return file;
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // get the file name
        // append a random unique string to identify each images
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // get the file path
        Path uploadDir = Paths.get("uploads/avatars");
        // if file path doesn't exist, create a new file path with path name is uploads
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // get the full file path to save the file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // then save the file
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @Override
    public void deleteUser(Long userId) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        user.setIsActive(IsActive.DISABLE);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long userId) throws DataNotFoundException {
        return UserResponse.fromUser(userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found")));
    }

    @Override
    public void updateUser(Long userId, UserUpdateDTO userUpdateDTO) throws DataNotFoundException {
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        updatedUser.setFullName(userUpdateDTO.getFullName());
        updatedUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        updatedUser.setAddress(userUpdateDTO.getAddress());
        updatedUser.setIsActive(userUpdateDTO.getIsActive());
        updatedUser.setRole(roleRepository.findById(userUpdateDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found!")));
        updatedUser.setAvatar(userUpdateDTO.getAvatar());
        updatedUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        userRepository.save(updatedUser);
    }

    @Override
    public int countCustomers() throws DataNotFoundException {
        Role role = roleRepository.findById(2L)
                .orElseThrow(() -> new DataNotFoundException("Don't have any customer"));
        return userRepository.countAllByRole(role);
    }

    @Override
    public List<CustomerStatisticResponse> getLatestCustomers() {
        return userRepository.findTop4ByOrderByIdDesc().stream().map(this::convertToCustomerStatistic).toList();
    }

    private CustomerStatisticResponse convertToCustomerStatistic(User user) {
        return CustomerStatisticResponse.builder()
                .id(user.getId())
                .name(user.getFullName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}
