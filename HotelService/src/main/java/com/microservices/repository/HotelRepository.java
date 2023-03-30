package com.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,String> {

}
