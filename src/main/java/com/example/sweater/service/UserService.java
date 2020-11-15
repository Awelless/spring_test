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

import javax.validation.ValidationException;
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

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
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

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public boolean updateProfile(User user, String password, String email) {

        String userEmail = user.getEmail();

        boolean isUpdated = false;
        boolean isEmailChanged = email != null && email != "" && !email.equals(userEmail);

        if (isEmailChanged) {
            user.setEmail(email);
            isUpdated = true;

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
            isUpdated = true;
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }

        return isUpdated;
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
}
