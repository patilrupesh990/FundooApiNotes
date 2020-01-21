package com.bridgelabz.notes.service;

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
	String status400="No notes Available or UserToken Expired";
	@Transactional
	public ResponseEntity<NotesResponce> createNote(Note note, String token) {
		Long id = getUserIdFromToken(token);
		Note noteTemp = noteDao.createNote(id, note);
		if (id != 0) {
			if (noteTemp != null)
				return ResponseEntity.status(HttpStatus.CREATED).body(new NotesResponce(201, "Note Created"));
			else
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
						.body(new NotesResponce(424, "Some internal Errors !!Be patients we will Solve Sorty"));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new NotesResponce(424, "USer Does not exist"));
	}

	public long getUserIdFromToken(String token) {
		return tokenUtil.parseToken(token);
	}

	@Transactional
	public ResponseEntity<NotesResponce> update(Long noteId, Note noteDTO, String token) {
		log.info("abccc");
		try {
			Note note = noteDao.getNoteById(noteId);
			Long userId = getUserIdFromToken(token);
			if (userId != 0) {
				if (noteDTO.getTitle() != null)
					note.setTitle(noteDTO.getTitle());
				if (noteDTO.getDescription() != null)
					note.setDescription(noteDTO.getDescription());
				if (noteDao.updateNote(noteId, note, userId) != null)
					return ResponseEntity.status(HttpStatus.OK).body(new NotesResponce(200, "Note Updated"));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotesResponce(400, "Note Can't Updated"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new NotesResponce(400, "User Does Not exist"));
		}
	}

	@Transactional
	public ResponseEntity<String> delete(Long noteId, String token) {
		Long userId = getUserIdFromToken(token);
		try {
			if (noteDao.deleteNote(userId, noteId) == 1) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note deleted SuccessFully");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Notes unavailable");
			}
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does Note Exist" + e);
		}
	}

	@Transactional
	public ResponseEntity<NotesResponce> getNoteList(String token) {
		log.info("Number of Notes Available" + noteDao.getAllNoteList(getUserIdFromToken(token)).size());
		try {
			if (noteDao.getAllNoteList(getUserIdFromToken(token)).isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new NotesResponce(400, "No any Notes Available"));
			else
				return ResponseEntity.status(HttpStatus.FOUND)
						.body(new NotesResponce(200,
								"Number of Notes Available Are: "
										+ noteDao.getAllNoteList(getUserIdFromToken(token)).size(),
								noteDao.getAllNoteList(getUserIdFromToken(token))));
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, "Pin Operation Sucessfully done"));
		}
	}

	@Transactional
	public ResponseEntity<NotesResponce> pinnedNotes(String token, Long noteId) {
		try {
			Long userId = getUserIdFromToken(token);
			Integer result=noteDao.pinUnpinNote(userId, noteId);
			if (result == 1) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note Pinned Sucessfully"));
			}else if(result == 2){
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note Unpinned Sucessfully"));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new NotesResponce(400, status400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, status400));
		}
	}

	@Transactional
	public ResponseEntity<NotesResponce> trashedNote(String token, Long noteId) {
		try {
			getUserIdFromToken(token);
			Integer result=noteDao.trashedNote(noteId);
			if (result == 1) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note successfully moved to Trashed"));
			} else if (result == 2) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note successfully Restored"));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new NotesResponce(400, status400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, status400));
		}
	}
	
	public ResponseEntity<NotesResponce> getTrashNotes(String token) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new NotesResponce(202,
							"Total Trashed Notes Are:" + noteDao.getTrashedNoteList(getUserIdFromToken(token)).size(),
							noteDao.getTrashedNoteList(getUserIdFromToken(token))));
		} catch (Exception e) {
			log.info("get Trashed" + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new NotesResponce(400, status400 ));
		}
	}

	@Transactional
	public ResponseEntity<NotesResponce> archivedNote(String token, Long noteId) {
		try {
			getUserIdFromToken(token);
			Integer result=noteDao.archivedNote(noteId);
			if (result== 1) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note Successfully moved in Achive Folder"));
			} else if (result == 2) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new NotesResponce(202, "Note Successfully moved from Achive Folder to DashBoard"));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new NotesResponce(400, status400));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NotesResponce(400, status400));
		}
	}
	
	
	public ResponseEntity<NotesResponce> getArchivedNotes(String token) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new NotesResponce(202,
							"Total Achived Notes Are:" + noteDao.getArchiveNoteList(getUserIdFromToken(token)).size(),
							noteDao.getTrashedNoteList(getUserIdFromToken(token))));
		} catch (Exception e) {
			log.info("get Archived" + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new NotesResponce(400, status400 ));
		}
	}
	
//	public  ResponseEntity<NotesResponce> getAllNotesByLabel(String token,String labelNAme)
//	{
//		
//	}

}
