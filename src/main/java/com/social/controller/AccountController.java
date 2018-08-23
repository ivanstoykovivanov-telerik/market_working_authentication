package com.social.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.social.service.UserService;
import com.social.util.CustomErrorType;
import com.social.model.User;

@CrossOrigin
@RestController
@RequestMapping("account")
public class AccountController {

	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;

	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User newUser) {

		System.out.println("****************  USER ***********");
		System.out.println(newUser.toString());

		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());
			return new ResponseEntity(
					new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
					HttpStatus.CONFLICT);
		}
		newUser.setRole("USER");
		
		return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
	}


	@CrossOrigin
	@RequestMapping("/login")
	public Principal user(Principal principal) {
		logger.info("user logged "+ principal);
		return principal;
	}

	@CrossOrigin
	@RequestMapping("/logout")
	public Principal userLogOut(Principal principal) {
		logger.info("user logged out "+ principal);
		return principal;
	}

	@CrossOrigin
	@GetMapping
	public List<User> user() {
		return userService.findAll();
	}

	@PostMapping("/save")
	public User save(@RequestBody User user) {
		return userService.update(user);
	}

}
