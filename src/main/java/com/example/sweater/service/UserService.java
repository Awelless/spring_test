package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.exception.UserNotUniqueException;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) throws UserNotUniqueException {

        Map<String, String> errors = new TreeMap<>();

        if (userRepo.findByEmail(user.getEmail()) != null) {
            errors.put("emailError", "Email is already taken");
        }
        if (userRepo.findByUsername(user.getUsername()) != null) {
            errors.put("usernameError", "Username " + user.getUsername() + " is not available");
        }

        if (errors.size() > 0) {
            throw new UserNotUniqueException(errors);
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendActivationMessage(user);

        return true;
    }

    public boolean updateUser(User user, String password, String email) throws UserNotUniqueException {

        String userEmail = user.getEmail();

        boolean isUpdated = false;
        boolean isEmailChanged = email != null && email != "" && !email.equals(userEmail);

        if (isEmailChanged) {

            if (userRepo.findByEmail(email) != null) {
                throw new UserNotUniqueException("emailError", "Email is already taken");
            }

            user.setNewEmail(email);
            isUpdated = true;

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        } else {
            user.setActivationCode(null);
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
            isUpdated = true;
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendUpdateEmailMessage(user);
        }

        return isUpdated;
    }

    private void sendUpdateEmailMessage(User user) {
        if (!StringUtils.isEmpty(user.getNewEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "To update your e-mail address, visit next link: http://localhost:8080/settings/update/%s",
                    user.getUsername(), user.getActivationCode()
            );

            mailSender.send(user.getNewEmail(), "Account update", message);
        }
    }

    private void sendActivationMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(), user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public User activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return null;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);

        return user;
    }

    public User updateEmail(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return null;
        }

        user.setActivationCode(null);
        user.setEmail(user.getNewEmail());
        user.setNewEmail(null);
        userRepo.save(user);

        return user;
    }

    public List<User> findAll() {
        List<User> userList = userRepo.findAll();
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User a, User b) {
                return a.getUsername().compareTo(b.getUsername());
            }
        });
        return userList;
    }

    public void saveUser(User user, String username) {
        user.setUsername(username);
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }

    public List<User> findByPattern(String pattern, Pageable pageable) {
        List<User> suitableUsers = userRepo.findAll()
                .stream()
                .filter(user -> user.getUsername().contains(pattern))
                .collect(Collectors.toList());

        suitableUsers.sort(new Comparator<User>() {
            @Override
            public int compare(User a, User b) {
                return b.getSubscribers().size() - a.getSubscribers().size();
            }
        });

        return suitableUsers;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void resetPassword(User user) {
        user.setActivationCode(UUID.randomUUID().toString());

        sendResetPasswordMessage(user);
    }

    private void sendResetPasswordMessage(User user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "To reset your password, visit next link: http://localhost:8080/reset/%s",
                user.getUsername(), user.getActivationCode()
        );

        mailSender.send(user.getEmail(), "Password reset", message);
    }

    public User findByActivationCode(String code) {
        return userRepo.findByActivationCode(code);
    }
}
