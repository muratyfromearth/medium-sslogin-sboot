package com.cloudnesil.medium.userlogin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudnesil.medium.userlogin.domain.User;
import com.cloudnesil.medium.userlogin.repository.error.ApiErrors;
import com.cloudnesil.medium.userlogin.repository.user.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository; 
	
	@PostMapping("/api/user/create")
	public ResponseEntity<?> saveUser(@RequestBody @Valid User user) {
		
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			ApiErrors errors = new ApiErrors();
			errors.setMessage("Try another user name.");
			errors.getValidationErrors().put("invalidusername", "User name already exists, choose another one!");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
		
		userRepository.save(user);
		
		return ResponseEntity.ok("User created");		
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	private ResponseEntity<?> handleFieldErrors(MethodArgumentNotValidException ex) {
		ApiErrors apiErrors = new ApiErrors();
		
		apiErrors.setMessage("Validation error(s) exist(s).");
		ex.getBindingResult().getFieldErrors().parallelStream()
			.forEach(error -> apiErrors.getValidationErrors().put("invalid" + error.getField(), error.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrors);
		
	}
}





