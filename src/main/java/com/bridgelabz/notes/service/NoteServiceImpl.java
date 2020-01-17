package com.bridgelabz.notes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dao.INotesDao;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.NoteCreatedResponse;
import com.bridgelabz.notes.response.NotesResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImpl {
	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	INotesDao noteDao;

	@Autowired
	NoteCreatedResponse notCreatedResponse;
	@Autowired
	NotesResponce noteObjectResponse;

	@Transactional
	public ResponseEntity<NoteCreatedResponse> createNote(Note note, String token) {
		Long id = getUserIdFromToken(token);
		Note noteTemp = noteDao.createNote(id, note);
		if (id != 0) {
			notCreatedResponse.setId(id);
			notCreatedResponse.setName(note.getName());
			notCreatedResponse.setTitle(note.getTitle());
			if (noteTemp != null)
				return ResponseEntity.status(HttpStatus.CREATED).body(notCreatedResponse);
			else
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(notCreatedResponse);
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(notCreatedResponse);
	}

	public long getUserIdFromToken(String token) {
		return tokenUtil.parseToken(token);
	}

	@Transactional
	public ResponseEntity<NotesResponce> update(Long noteId, Note noteDTO, String token) {
		log.info("abccc");
		Note note = noteDao.getNoteById(noteId);
		System.out.println("m");
		Long userId = getUserIdFromToken(token);
		if (userId != 0) {
			if (noteDTO.getTitle() != null)
				note.setTitle(noteDTO.getTitle());
			if (noteDTO.getDescription() != null)
				note.setDescription(noteDTO.getDescription());
			if (noteDao.updateNote(noteId, note, userId) != null)
				return ResponseEntity.status(HttpStatus.OK).body(new NotesResponce(200,"Note Updated"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400,"Note Can't Updated"));
	}
	
	@Transactional
	public ResponseEntity<String> delete(Long noteId, String token) {
		Long userId = getUserIdFromToken(token);
		if (noteDao.deleteNote(userId, noteId) == 1) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note deleted SuccessFully");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Notes unavailable");
		}
	}
	
	@Transactional
	public List<Note> getNoteList(String token)
	{
		noteDao.getAllNoteList(getUserIdFromToken(token));
		
		return null;
	}

}
