package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Location origin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Location destination;
    
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal distance;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal bill;
    
    @Builder.Default
    @Column(name = "status_id", nullable = false)
    private TripStatus status = TripStatus.NEW;
    
    @Builder.Default
    @ToString.Exclude
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "trip_car", 
        joinColumns = { @JoinColumn(name = "trip_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "car_id") }
    )
    private Set<Car> cars = new HashSet<>();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Trip other = (Trip) obj;
        return id != 0 &&  id == other.id;
    }
}
