package com.example.taxiservicespring.service.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;

import org.hibernate.annotations.NaturalId;

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
@NamedNativeQuery(
        name = "Car.findByCategoryIdAndStatusAndCapacity",
        query = "SELECT * FROM car c INNER JOIN car_model cm ON c.model_id = cm.id "
                + "WHERE c.category_id = ?1 AND c.status_id = ?2 AND cm.seat_count >= ?3 "
                + "ORDER BY cm.seat_count LIMIT 1",
        resultClass = Car.class)
public class Car {

    @Id
    @GeneratedValue
    private long id;

    @NaturalId
    @Column(length = 50)
    private String regNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Builder.Default
    @Column(name = "status_id", nullable = false)
    private CarStatus status = CarStatus.READY;

    @Override
    public int hashCode() {
        return Objects.hash(getRegNumber());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        return Objects.equals(getRegNumber(), other.getRegNumber());
    }
}
