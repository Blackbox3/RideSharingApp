package com.event.eventweb.registration;

import com.event.eventweb.config.ResponseMsg;
import com.event.eventweb.dto.PlatformResponse;
import com.event.eventweb.entity.ApplicationUser;
import com.event.eventweb.registration.dto.ApplicationUserDTO;
import com.event.eventweb.registration.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anusha Shresthah
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationManager registrationManager;
    @Autowired
    private RegistrationMapper registrationMapper;


    @Override
    public PlatformResponse registerUser(RegistrationRequest registrationRequest) {
       PlatformResponse platformResponse= registrationManager.checkIfApplicationUserAlreadyExists(registrationRequest.getUsername());
       if(!platformResponse.isSuccess()){
           return platformResponse;
       }

        ApplicationUser applicationUser = registrationMapper.convertToApplicationUser(registrationRequest);

        ApplicationUserDTO applicationUserDTO = registrationMapper.convertToApplicationUserDTO(registrationManager.saveApplicationUser(applicationUser));

        return ResponseMsg.successResponse("User Registered Successfully", applicationUserDTO);
    }
}
