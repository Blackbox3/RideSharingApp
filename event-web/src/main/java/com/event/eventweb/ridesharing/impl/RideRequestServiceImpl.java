package com.event.eventweb.ridesharing.impl;

import com.event.eventweb.config.ResponseMsg;
import com.event.eventweb.dto.PlatformResponse;
import com.event.eventweb.entity.Requests;
import com.event.eventweb.repository.RequestRepository;
import com.event.eventweb.ridesharing.RideRequestService;
import com.event.eventweb.ridesharing.dto.RideRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bikash Shah
 */
@Service
public class  RideRequestServiceImpl implements RideRequestService {

    @Autowired
    private RequestRepository requestRepository;


    @Override
    public PlatformResponse createRide(RideRequestDTO rideRequestDTO) {

        Requests requests = new Requests();
        requests.setRiderId(rideRequestDTO.getRiderId());
        requests.setRiderName(rideRequestDTO.getRiderName());
        requests.setAmount(rideRequestDTO.getAmount());
        requests.setStartLocationName(rideRequestDTO.getStartLocationName());
        requests.setEndLocationName(rideRequestDTO.getEndLocationName());
        requests.setStartLocationLatitude(rideRequestDTO.getStartLocationLatitude());
        requests.setStartLocationLongitude(rideRequestDTO.getStartLocationLongitude());
        requests.setEndLocationLatitude(rideRequestDTO.getEndLocationLatitude());
        requests.setEndLocationLongitude(rideRequestDTO.getEndLocationLongitude());
        requests.setDistance(rideRequestDTO.getDistance());
        requests = requestRepository.save(requests);

        return ResponseMsg.successResponse("Ride request created successfully",convertToRideRequestDTO(requests,rideRequestDTO));
    }


    private static RideRequestDTO convertToRideRequestDTO(Requests requests,RideRequestDTO rideRequestDTO){
        rideRequestDTO.setId(requests.getId());
        return rideRequestDTO;
    }
}
