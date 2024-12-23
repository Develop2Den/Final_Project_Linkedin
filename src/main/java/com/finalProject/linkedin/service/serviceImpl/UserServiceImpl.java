package com.finalProject.linkedin.service.serviceImpl;

import com.finalProject.linkedin.dto.request.user.CreateUserReq;
import com.finalProject.linkedin.dto.responce.user.CreateUserRes;
import com.finalProject.linkedin.entity.User;
import com.finalProject.linkedin.repository.UserRepository;
import com.finalProject.linkedin.service.serviceIR.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    public final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public CreateUserRes createUser(CreateUserReq createUserReq) {

        User user = modelMapper.map(createUserReq, User.class);

        if (createUserReq.getPassword() != null) {
            String plainPassword = createUserReq.getPassword().getPassword();
            user.setPassword(passwordEncoder.encode(plainPassword));
            user.setIsVerified(true);
        }

        User savedUser = save(user);
        return modelMapper.map(savedUser, CreateUserRes.class);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setUserId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public CreateUserRes updateUser(Long id, CreateUserReq createUserReq) {
        User user = modelMapper.map(createUserReq, User.class);
        user.setUserId(id);
        User updatedUser = updateUser(id, new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword())
        ));
        return updatedUser != null ? modelMapper.map(updatedUser, CreateUserRes.class) : null;
    }

    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        return deleteById(id);
    }

    public CreateUserRes getCurrentUser(String email) {
        Optional<User> optionalUser = findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return modelMapper.map(user, CreateUserRes.class);
        } else {
            throw new UsernameNotFoundException("Customer not found with email: " + email);
        }
    }

    public User getOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public CreateUserRes getUser(Long id) {
        User user = getOne(id);
        return user != null ? modelMapper.map(user, CreateUserRes.class) : null;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @Transactional
    public void removeUnverifiedUsers() {
        LocalDateTime now = LocalDateTime.now();
        List<User> unverifiedUsers = userRepository.findUnverifiedUsersWithExpiredTokens(now);

        if (!unverifiedUsers.isEmpty()) {
            userRepository.deleteAll(unverifiedUsers);
            log.warn("Deleted " + unverifiedUsers.size() + " unverified users with expired tokens");
        }
    }

    public Boolean isUserVerified(String email) {
        return userRepository.findIsVerifiedByEmail(email);
    }

}
