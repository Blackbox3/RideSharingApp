package com.event.eventweb.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;


@Entity
@Table(name = "REQUESTS")
@Data
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 22)
    private Long id;

    @Column(name = "RIDER_ID", nullable = false, length = 50)
    private Long riderId;

    @Column(name = "DRIVER_ID", length = 50)
    private Long driverId;

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "RIDER_NAME")
    private String riderName;

    @Column(name = "AMOUNT", length = 50)
    private Double amount;

    @Column(name = "START_LOCATION_NAME", length = 50)
    private String startLocationName;

    @Column(name = "END_LOCATION_NAME", nullable = false, length = 50)
    private String endLocationName;

    @Column(name = "START_LOCATION_LATITUDE", length = 50)
    private String startLocationLatitude;

    @Column(name = "START_LOCATION_LONGITUDE", nullable = false, length = 50)
    private String startLocationLongitude;

    @Column(name = "END_LOCATION_LATITUDE", length = 50)
    private String endLocationLatitude;

    @Column(name = "END_LOCATION_LONGITUDE", nullable = false, length = 50)
    private String endLocationLongitude;

    @Column(name = "DRIVER_LOCATION_LATITUDE", length = 50)
    private String driverLocationLatitude;

    @Column(name = "DRIVER_LOCATION_LONGITUDE", length = 50)
    private String driverLocationLongitude;

    @Column(name = "DRIVER_LOCATION_NAME")
    private String driverLocationName;

    @Column(name = "DISTANCE_IN_KM")
    private String distance;

}
