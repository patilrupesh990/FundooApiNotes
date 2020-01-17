package com.bridgelabz.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.NoteCreatedResponse;
import com.bridgelabz.notes.response.NotesResponce;
import com.bridgelabz.notes.service.NoteServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/notes")
public class NoteController 
{
	@Autowired
	NoteServiceImpl noteService;
	

	@PostMapping("/create/{token}")
	public ResponseEntity<NoteCreatedResponse> createNote(@RequestBody Note note, @PathVariable("token") String token) {
		log.info("Note Controller createdResponse");
		return noteService.createNote(note, token);
	}
	
	@PutMapping("/update/{token}")
	public ResponseEntity<NotesResponce> update(@RequestBody Note noteDTO,@RequestHeader String token)
	{
		log.info("abc");
		return noteService.update(noteDTO.getId(), noteDTO, token);	
	}
	
	@DeleteMapping("/delete/{token}")
	public ResponseEntity<String> delete(@RequestParam("noteId") Long noteId,@RequestHeader String token)
	{
		return noteService.delete(noteId, token);
	}
	
	
	
	
	
}
