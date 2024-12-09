package com.event.eventweb.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Anusha Shresthah
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "application_user")
@Table(name = "APPLICATION_USER")
@Data
public class ApplicationUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 22)
    private Long id;


    @Basic(optional = false)
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Basic(optional = false)
    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Basic(optional = false)
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Basic(optional = true)
    @Column(name = "EMAIL_ADDRESS", nullable = true, length = 200)
    private String emailAddress;

    @Basic(optional = false)
    @Column(name = "ACTIVE", nullable = false)
    private char active;

    @Basic(optional = true)
    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name="MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name="LICENSE_NUMBER")
    
    private String licenseNumber;

    @Column(name = "BIKE_NAME")
    private String bikeName;
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;




    public ApplicationUser() {
    }

    public ApplicationUser(Long id) {
        this.id = id;
    }

    public ApplicationUser(String username, String name, String emailAddress) {
        this.username = username;
        this.name = name;
        this.emailAddress = emailAddress;
    }
}
