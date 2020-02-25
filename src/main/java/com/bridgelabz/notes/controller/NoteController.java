package com.bridgelabz.notes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.bridgelabz.notes.service.NoteServiceImpl;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaClient
@RestController
@Slf4j
@RequestMapping("/notes")
@CrossOrigin("*")
public class NoteController {
	@Autowired
	NoteServiceImpl noteService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Object> createNote(@Valid @RequestBody Note note, @RequestHeader String token) {
		log.info("Note Controller createdResponse");
		return noteService.createNote(note, token);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestBody Note noteDTO, @RequestHeader String token) {
		log.info("abc");
		return noteService.update(noteDTO.getId(), noteDTO, token);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> delete(@RequestParam("noteId") Long noteId, @RequestHeader String token) {
		return noteService.delete(noteId, token);
	}

	@GetMapping("/allnotes")
	public ResponseEntity<Object> getAllNotes(@RequestHeader String token) {
		log.info("getAllNotes Controller called...");
		return noteService.getNoteList(token);
	}
	@GetMapping("/pinned/notes")
	public ResponseEntity<Object> getAllPinnedNotes(@RequestHeader String token) {
		return noteService.getPinnedNoteList(token);
	}

	@PutMapping("/pin")
	public ResponseEntity<Object> pinnedNote(@RequestHeader String token, @RequestParam("noteId") Long noteId) {
		return noteService.pinnedNotes(token, noteId);
	}

	@PutMapping("/trash")
	public ResponseEntity<Object> moveToTrase(@RequestHeader String token, @RequestParam("noteId") Long noteId) {
		return noteService.trashedNote(token, noteId);
	}

	@GetMapping("/trash/notes")
	public ResponseEntity<Object> getAllTrashedNotes(@RequestHeader String token) {
		return noteService.getTrashNotes(token);
	}

	@PutMapping("/archive")
	public ResponseEntity<Object> moveToAchive(@RequestHeader String token,
			@RequestParam("noteId") Long noteId) {
		return noteService.archivedNote(token, noteId);
	}

	@GetMapping("/archive/notes")
	public ResponseEntity<Object> getAllArchivedNotes(@RequestHeader String token) {
		return noteService.getArchivedNotes(token);
	}
	
	@GetMapping("notes")
	public ResponseEntity<Object> getNoteById(@RequestHeader String token,@RequestParam("noteId") Long noteId) {
		return noteService.getNoteById(token,noteId);
	}
	@GetMapping("notes/color")
	public ResponseEntity<Object> addColor(@RequestHeader String token,@RequestParam("noteId") Long noteId,@RequestHeader String color) {
		return noteService.addColor(color, token, noteId);
	}
	
}
