package com.microservices.servicesimpl;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.entities.Hotel;
import com.microservices.entities.Rating;
import com.microservices.entities.User;
import com.microservices.exceptions.ResourceNotFoundException;
import com.microservices.external.services.HotelService;
import com.microservices.repository.UserRepository;
import com.microservices.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HotelService hotelService;
	

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
//		ArrayList<List<Rating>> ratingsForUsers = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId,ArrayList.class);
//	       user.setRatings(ratingsForUser); 
//		   logger.info("{}",ratingsForUser); 
		return users;
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given id is not found on server!!"));
        Rating[] ratingsForUser = restTemplate.getForObject("http://RATINGS-SERVICE/ratings/users/"+userId,Rating[].class);
       List<Rating> ratings = Arrays.stream(ratingsForUser).toList();
  
	   List<Rating> ratingList = ratings.stream().map(rating -> {
//		   ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
//		   Hotel hotel = forEntity.getBody();
		   
		   Hotel hotel = hotelService.getHotel(rating.getHotelId());
		
		  
		   
		   
		   rating.setHotel(hotel);
		   return rating;
	   }).collect(Collectors.toList());
	   
	   user.setRatings(ratingList); 
	   logger.info("{}",ratingList); 
	   return user;
	 }

}
