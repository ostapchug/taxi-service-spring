package com.example.taxiservicespring.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "brand", "name", "color", "year" }) })
public class CarModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 50)
    private String brand;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String color;

    private int year;
    private int seatCount;

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
        CarModel other = (CarModel) obj;
        return id != 0 && id == other.id;
    }
}
