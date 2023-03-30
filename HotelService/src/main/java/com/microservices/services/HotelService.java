package com.microservices.services;

import java.util.List;

import com.microservices.entities.Hotel;

public interface HotelService {

	Hotel create(Hotel hotel);
	
	List<Hotel> getAll();
	
	Hotel get(String id);
}
