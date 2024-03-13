package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.repositories.RoleRepository;
import com.duchung.shopappspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
            String encodedPassword = 
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        return null;
    }
}
