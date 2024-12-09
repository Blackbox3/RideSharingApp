package com.event.eventweb.registration;

import com.event.eventweb.entity.ApplicationUser;
import com.event.eventweb.registration.dto.ApplicationUserDTO;
import com.event.eventweb.registration.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Anusha Shresthah
 */
@Component
public class RegistrationMapper {

    @Autowired
    private RegistrationManager registrationManager;

    public ApplicationUser convertToApplicationUser(RegistrationRequest registrationRequest) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(registrationRequest.getUsername());
        applicationUser.setPassword(registrationManager.generatePassword(registrationRequest.getPassword()));
        applicationUser.setName(registrationManager.generateFullName(registrationRequest.getFirstName(), registrationRequest.getLastName()));
        applicationUser.setEmailAddress(registrationRequest.getEmailAddress());
        applicationUser.setActive('Y');
        applicationUser.setCreatedDate(new Date());
        applicationUser.setFirstName(registrationRequest.getFirstName());
        applicationUser.setLastName(registrationRequest.getLastName());
        applicationUser.setMobileNumber(registrationRequest.getMobileNumber());
        applicationUser.setLicenseNumber(registrationRequest.getLicenseNumber());
        applicationUser.setBikeName(registrationRequest.getBikeName());
        applicationUser.setPlateNumber(registrationRequest.getPlateNumber());
        applicationUser.setUserType(registrationRequest.getUserType());
        applicationUser.setName(registrationRequest.getName());


        return applicationUser;
    }

    public ApplicationUserDTO convertToApplicationUserDTO(ApplicationUser applicationUser){
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        applicationUserDTO.setUsername(applicationUser.getUsername());
        applicationUserDTO.setEmailAddress(applicationUser.getEmailAddress());
        return applicationUserDTO;
    }
}
