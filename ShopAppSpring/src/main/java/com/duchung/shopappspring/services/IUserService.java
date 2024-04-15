package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.UserDTO;
import com.duchung.shopappspring.dtos.UserLoginDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.exceptions.UsernameOrPasswordIsWrong;
import com.duchung.shopappspring.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException, DataExistedException, InvalidParameterException;
    String login(UserLoginDTO userLoginDTO) throws InvalidParameterException, UsernameOrPasswordIsWrong;

    User getUserDetailsFromToken(String token) throws Exception;
}
