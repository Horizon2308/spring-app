package com.duchung.shopappspring.services;

import com.duchung.shopappspring.components.JwtTokenUtils;
import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.exceptions.*;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.repositories.RoleRepository;
import com.duchung.shopappspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

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
                .isActive(true)
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
}
