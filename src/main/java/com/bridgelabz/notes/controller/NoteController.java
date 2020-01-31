package com.bridgelabz.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.NotesResponce;
import com.bridgelabz.notes.service.NoteServiceImpl;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaClient
@RestController
@Slf4j
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	NoteServiceImpl noteService;

	@PostMapping("/create/{token}")
	public ResponseEntity<Object> createNote(@RequestBody Note note, @PathVariable("token") String token) {
		log.info("Note Controller createdResponse");
		return noteService.createNote(note, token);
	}

	@PutMapping("/update/{token}")
	public ResponseEntity<Object> update(@RequestBody Note noteDTO, @RequestHeader String token) {
		log.info("abc");
		return noteService.update(noteDTO.getId(), noteDTO, token);
	}

	@DeleteMapping("/delete/{token}")
	public ResponseEntity<Object> delete(@RequestParam("noteId") Long noteId, @RequestHeader String token) {
		return noteService.delete(noteId, token);
	}

	@GetMapping("/allnotes/{token}")
	public ResponseEntity<Object> getAllNotes(@PathVariable("token") String token) {
		return noteService.getNoteList(token);
	}

	@PutMapping("/pin/{token}")
	public ResponseEntity<Object> pinnedNote(@RequestHeader String token, @RequestParam("noteId") Long noteId) {
		return noteService.pinnedNotes(token, noteId);
	}

	@PutMapping("/trash/{token}")
	public ResponseEntity<Object> moveToTrase(@RequestHeader String token, @RequestParam("noteId") Long noteId) {
		return noteService.trashedNote(token, noteId);
	}

	@GetMapping("/trash/notes/{token}")
	public ResponseEntity<Object> getAllTrashedNotes(@RequestHeader String token) {
		return noteService.getTrashNotes(token);
	}

	@PutMapping("/archive/{token}")
	public ResponseEntity<Object> moveToAchive(@RequestHeader String token,
			@RequestParam("noteId") Long noteId) {
		return noteService.archivedNote(token, noteId);
	}

	@GetMapping("/archive/notes/{token}")
	public ResponseEntity<Object> getAllArchivedNotes(@RequestHeader String token) {
		return noteService.getArchivedNotes(token);
	}
	
	@GetMapping("notes/{token}")
	public ResponseEntity<Object> getNoteById(@RequestHeader String token,@RequestParam("noteId") Long noteId) {
		return noteService.getNoteById(token,noteId);
	}
	
	
	
	public ResponseEntity<NotesResponce> getAllNotesByLabel(@RequestHeader String token,@RequestHeader String labelName)
	{
		return null;
	}
}
