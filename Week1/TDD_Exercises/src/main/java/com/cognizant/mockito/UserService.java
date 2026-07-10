package com.cognizant.mockito;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findUserById(id);
    }

    public void registerUser(User user) {
        // Business logic: check if email is not null before saving
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userRepository.saveUser(user);
        } else {
            throw new IllegalArgumentException("User email cannot be empty");
        }
    }
    
    public void removeUser(Long id) {
        userRepository.deleteUser(id);
    }
}
