package com.api.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Patron;
import com.api.services.PatronService;

@RestController
public class PatronController {
	
	@Autowired
	private PatronService patronService;
	
	@PostMapping("/addPatron")
	public ResponseEntity<?> addPatron(@RequestBody Patron patron) {
		if(patronService.existByPatronId(patron.getPid())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Patron with ID " + patron.getPid() + " already exists.");
		}
		Patron p=patronService.addPatron(patron);	
		 return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
	@GetMapping("/patronList")
	public List<Patron> getPatronList() {
		return this.patronService.getList();	
	}
	@DeleteMapping("/deletePatron/{pid}")
	public ResponseEntity<String> deletePatron(@PathVariable int pid) {
	    try {
	        patronService.deletePatron(pid);
	        return ResponseEntity.ok("delete " + pid + " successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	} 
	}

}