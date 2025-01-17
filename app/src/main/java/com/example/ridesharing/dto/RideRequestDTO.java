package com.example.ridesharing.dto;

public class RideRequestDTO {

    private Long id;
    private Long riderId;
    private String riderName;
    private Double amount;
    private String startLocationName;
    private String endLocationName;
    private String startLocationLatitude;
    private String startLocationLongitude;
    private String endLocationLatitude;
    private String endLocationLongitude;
    private String distance;

    public RideRequestDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStartLocationName() {
        return startLocationName;
    }

    public void setStartLocationName(String startLocationName) {
        this.startLocationName = startLocationName;
    }

    public String getEndLocationName() {
        return endLocationName;
    }

    public void setEndLocationName(String endLocationName) {
        this.endLocationName = endLocationName;
    }

    public String getStartLocationLatitude() {
        return startLocationLatitude;
    }

    public void setStartLocationLatitude(String startLocationLatitude) {
        this.startLocationLatitude = startLocationLatitude;
    }

    public String getStartLocationLongitude() {
        return startLocationLongitude;
    }

    public void setStartLocationLongitude(String startLocationLongitude) {
        this.startLocationLongitude = startLocationLongitude;
    }

    public String getEndLocationLatitude() {
        return endLocationLatitude;
    }

    public void setEndLocationLatitude(String endLocationLatitude) {
        this.endLocationLatitude = endLocationLatitude;
    }

    public String getEndLocationLongitude() {
        return endLocationLongitude;
    }

    public void setEndLocationLongitude(String endLocationLongitude) {
        this.endLocationLongitude = endLocationLongitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public RideRequestDTO(Long riderId, String riderName, Double amount, String startLocationName,
                       String endLocationName, String startLocationLatitude, String startLocationLongitude,
                       String endLocationLatitude, String endLocationLongitude, String distance) {
        this.riderId = riderId;
        this.riderName = riderName;
        this.amount = amount;
        this.startLocationName = startLocationName;
        this.endLocationName = endLocationName;
        this.startLocationLatitude = startLocationLatitude;
        this.startLocationLongitude = startLocationLongitude;
        this.endLocationLatitude = endLocationLatitude;
        this.endLocationLongitude = endLocationLongitude;
        this.distance = distance;
    }
}
