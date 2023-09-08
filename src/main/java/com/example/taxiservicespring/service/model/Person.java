package com.example.taxiservicespring.service.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class Person {

    @Id
    @GeneratedValue
    private long id;

    @NaturalId
    @Column(length = 10)
    private String phone;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String surname;

    @Builder.Default
    @Column(name = "role_id", nullable = false)
    private Role role = Role.CLIENT;

    @Override
    public int hashCode() {
        return Objects.hash(getPhone());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return Objects.equals(getPhone(), other.getPhone());
    }
}
