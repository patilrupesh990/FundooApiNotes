package com.bridgelabz.notes.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.notes.dao.INotesDao;
import com.bridgelabz.notes.exception.UserDoesNotExistException;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.NoteCreatedResponse;
import com.bridgelabz.notes.response.NotesResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;
import com.bridgelabz.notes.util.UserData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImpl {
	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	INotesDao noteDao;
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	NoteCreatedResponse notCreatedResponse;
	@Autowired
	NotesResponce noteObjectResponse;
	
	String status400 = "No notes Available";
	String userNotExist = "User Token Expired or User Does Note Exist";
	

	@Transactional
	public ResponseEntity<Object> createNote(Note note, String token) {
		if (verifyUser(token)) {
			if (noteDao.createNote(getUserIdFromToken(token), note) != null)
				return ResponseEntity.status(HttpStatus.CREATED).body(new NotesResponce(201, "Note Created"));
			else
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
						.body(new NotesResponce(424, "Some internal Errors !!Be patients we will Solve Sorty"));
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}

	public long getUserIdFromToken(String token) {
		return tokenUtil.parseToken(token);
	}

	@Transactional
	public ResponseEntity<Object> update(Long noteId, Note noteDTO, String token) {
		log.info("abccc");
		try {
			Note note = noteDao.getNoteById(noteId);
			if (verifyUser(token)) {
				note.setTitle(noteDTO.getTitle());
				note.setDescription(noteDTO.getDescription());
				noteDao.updateNote(noteId, note, getUserIdFromToken(token));
				return ResponseEntity.status(HttpStatus.OK).body(new NotesResponce(200, "Note Updated"));
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}

	@Transactional
	public ResponseEntity<Object> delete(Long noteId, String token) {
		Long userId = getUserIdFromToken(token);
		try {
			if (verifyUser(token)) {
				if (noteDao.deleteNote(userId, noteId) == 1)
					return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note deleted ");
				else
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Notes unavailable");

			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));

			}
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}

	@Transactional
	public ResponseEntity<Object> getNoteList(String token) {
		log.info("Number of Notes Available" + noteDao.getAllNoteList(getUserIdFromToken(token)).size());
		try {
			if (verifyUser(token)) {
				if (noteDao.getAllNoteList(getUserIdFromToken(token)).isEmpty())
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new NotesResponce(400, "No any Notes Available"));
				else
					return ResponseEntity.status(HttpStatus.FOUND)
							.body(new NotesResponce(200,
									"Number of Notes Available Are: "
											+ noteDao.getAllNoteList(getUserIdFromToken(token)).size(),
									noteDao.getAllNoteList(getUserIdFromToken(token))));
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
			}
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, "Pin Operation Sucessfully done"));
		}
	}

	@Transactional
	public ResponseEntity<Object> pinnedNotes(String token, Long noteId) {
		try {
			if (verifyUser(token)) {
				Long userId = getUserIdFromToken(token);
				Integer result = noteDao.pinUnpinNote(userId, noteId);
				if (result == 1)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note Pinned"));
				else if (result == 2)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note Unpinned"));
				else
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, status400));

			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, status400 + "or User Token Expired Or UserDoes Not Exist"));
		}
	}

	@Transactional
	public ResponseEntity<Object> trashedNote(String token, Long noteId) {
		try {
			if (verifyUser(token)) {
				Integer result = noteDao.trashedNote(noteId);
				if (result == 1)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note Trashed"));
				else if (result == 2)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note Restored"));
				else
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, status400));

			} else
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, status400));
		}
	}

	public ResponseEntity<Object> getTrashNotes(String token) {
		try {
			if (verifyUser(token)) {
				log.info("Trashed Notes------>",noteDao.getTrashedNoteList(getUserIdFromToken(token)));
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202,
								"Total Trashed Notes Are:"
										+ noteDao.getTrashedNoteList(getUserIdFromToken(token)).size(),
								noteDao.getTrashedNoteList(getUserIdFromToken(token))));
				
			}else {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
			}
		} catch (Exception e) {
			log.info("get Trashed" + e);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new NotesResponce(400, status400));
		}
	}

	@Transactional
	public ResponseEntity<Object> archivedNote(String token, Long noteId) {
		try {
			getUserIdFromToken(token);
			Integer result = noteDao.archivedNote(noteId);
			if (verifyUser(token)) {
				if (result == 1)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note archived"));
				else if (result == 2)
					return ResponseEntity.status(HttpStatus.ACCEPTED)
							.body(new NotesResponce(202, "Note unarchived"));
				else
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, status400));

			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, status400));
		}
	}

	public ResponseEntity<Object> getArchivedNotes(String token) {
		if (verifyUser(token)) {
			try {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202,
								"Total Achived Notes Are:"
										+ noteDao.getArchiveNoteList(getUserIdFromToken(token)).size(),
								noteDao.getTrashedNoteList(getUserIdFromToken(token))));
			} catch (Exception e) {
				log.info("get Archived" + e);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new NotesResponce(400, status400));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}
	
	public ResponseEntity<Object> getNoteById(String token,Long tokenId)
	{
		if (verifyUser(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new NotesResponce(202,"Note Found",noteDao.getNoteById(tokenId)));
		}else
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}

	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		UserData userData = restTemplate.getForObject("http://localhost:8086/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
				+ (userData.getId() == getUserIdFromToken(token)));
		return (userData.getId() == getUserIdFromToken(token));
	}

}
