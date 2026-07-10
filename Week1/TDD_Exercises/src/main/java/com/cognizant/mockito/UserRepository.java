package com.cognizant.mockito;

public interface UserRepository {
    User findUserById(Long id);
    void saveUser(User user);
    void deleteUser(Long id);
}
