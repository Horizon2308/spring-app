package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.dtos.UserUpdateDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.exceptions.UsernameOrPasswordIsWrong;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.responses.CustomerStatisticResponse;
import com.duchung.shopappspring.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException, DataExistedException, InvalidParameterException;
    String login(UserLoginDTO userLoginDTO) throws InvalidParameterException, UsernameOrPasswordIsWrong;

    User getUserDetailsFromToken(String token) throws Exception;

    Page<UserResponse> getAllStaffs(String keyword, Pageable pageable);

    void uploadAvatar(Long userId, MultipartFile file) throws DataNotFoundException, InvalidParameterException, IOException;

    void deleteUser(Long userId) throws DataNotFoundException;

    UserResponse getUser(Long userId) throws DataNotFoundException;

    void updateUser(Long userId, UserUpdateDTO userUpdateDTO) throws DataNotFoundException;

    int countCustomers() throws DataNotFoundException;

    int countStaffs() throws DataNotFoundException;

    List<CustomerStatisticResponse> getLatestCustomers();
}
