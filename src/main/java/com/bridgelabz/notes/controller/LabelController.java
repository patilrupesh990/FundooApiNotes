package com.bridgelabz.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.response.LabelResponce;
import com.bridgelabz.notes.service.ILabelService;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaClient
@RestController
@RequestMapping("/labels")
@Slf4j
public class LabelController {

	@Autowired
	ILabelService labelService;

	@PostMapping("/add")
	public ResponseEntity<Object> addLable(@RequestHeader String token, @RequestHeader Long labelId,
			@RequestHeader Long noteId) {
		return labelService.addLabel(token, labelId, noteId);
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createLabel(@RequestHeader String token, @RequestBody Label label) {
		log.info("Create Label Controller");
		return labelService.createLable(token, label);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteLabel(@RequestHeader String token, @RequestHeader Long labelId) {
		return labelService.deleteLabel(token, labelId);
	}

	@PutMapping("/edit")
	public ResponseEntity<Object> editLabel(@RequestHeader Long labelId, @RequestHeader String newName,
			@RequestHeader String token) {
		return labelService.editLabel(labelId, newName, token);
	}

	@GetMapping("/label")
	public ResponseEntity<Object> getAllLabels(@RequestHeader String token) {
		return labelService.getAllLabels(token);
	}
	@GetMapping("/notes")
	public List<Object[]> getNotesByLabelId(@RequestHeader String token,@RequestHeader Long labelId) {
		return labelService.getNotesByLabelId(token, labelId);
	}
}
