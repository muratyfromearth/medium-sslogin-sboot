package com.cloudnesil.medium.userlogin.repository.error;

import java.util.HashMap;
import java.util.Map;

public class ApiErrors {
	public String message;
	public Map<String, String> validationErrors = new HashMap<>();
	
	//getters and setters
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}



