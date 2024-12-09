package com.event.eventweb.registration.dto;

import com.event.eventweb.dto.ModelBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Anusha Shresthah
 */
@Getter
@Setter
public class RegistrationRequest extends ModelBase {

    private String username;
    private String password;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String dateOfBirth;
    private String licenseNumber;
    private String bikeName;
    private String plateNumber;
    private String userType;
    private String name;

}
