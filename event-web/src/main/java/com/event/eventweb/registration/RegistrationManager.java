package com.event.eventweb.registration;

import com.event.eventweb.dto.PlatformResponse;
import com.event.eventweb.entity.ApplicationUser;
import com.event.eventweb.exception.ResourceAlreadyExistException;
import com.event.eventweb.repository.ApplicationUserRepository;
import com.event.eventweb.response.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Anusha Shresthah
 */
@Component
public class RegistrationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public PlatformResponse checkIfApplicationUserAlreadyExists(String username) {
        Optional<ApplicationUser> optionalApplicationUser = applicationUserRepository.findByUserName(username);
        if (optionalApplicationUser.isPresent()) {
            return ResponseMsg.failureResponse("Username "+username+" already exists");
        }
        return ResponseMsg.successResponse();
    }

    public String generatePassword(String username){
        return passwordEncoder.encode(username);
    }

    public String generateFullName(String firstName, String lastName) {
        // Concatenate the first name and last name with a space in between
        return firstName + " " + lastName;
    }

    public ApplicationUser saveApplicationUser(ApplicationUser applicationUser){
        return applicationUserRepository.save(applicationUser);
    }
}
