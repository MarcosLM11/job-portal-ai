package com.jobportal.userservice.service;

import com.jobportal.userservice.domain.User;
import com.jobportal.userservice.domain.UserStatus;
import com.jobportal.userservice.dto.UpdateUserRequest;
import com.jobportal.userservice.dto.UserResponse;
import com.jobportal.userservice.exception.UserNotExistsException;
import com.jobportal.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import static com.jobportal.userservice.util.UserMapper.toDto;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotExistsException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotExistsException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserResponse updateUserProfile(String email, UpdateUserRequest request) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotExistsException::new);

        if (request.fullName() != null) user.setFullName(request.fullName());
        if (request.phone() != null) user.setPhone(request.phone());
        if (request.profileImage() != null) user.setProfileImage(request.profileImage());

        return toDto(user);
    }

    @Override
    @Transactional
    public UserResponse suspendUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotExistsException::new);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(Instant.now());
        return toDto(user);
    }

    @Override
    @Transactional
    public UserResponse activateUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotExistsException::new);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return toDto(user);
    }

    @Override
    @Transactional
    public UserResponse deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotExistsException::new);
        user.setStatus(UserStatus.DELETED);
        userRepository.delete(user);
        return toDto(user);
    }
}
