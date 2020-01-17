package com.bridgelabz.notes.service;

import java.util.List;

import com.bridgelabz.notes.model.Note;
public interface INoteService {

	

	public Note getNoteById(Long uId, Integer nId);

	public String update(Long noteId, Note noteDTO, String token); 

	public String create(Note noteDTO, String tocken);
	
	
	List<Note> getNoteList(Long uId, String noteCategory);

	List<Note> getAllNoteList(Integer uId);
	
	
	void deleteNote(Integer uId, Integer nId);


	public void moveToTrash(Integer nId);

	public List<Note> getTrashedNoteList(Integer uId);
	
	//public void collaborateUser(User cUser, Note cNote);
	
	public Note getCompleteNoteById(Integer nId);

	//public void unCollaborate(Note cNote, User cUser);
	
	public List<Note> getCompleteTrashedNoteList();
	
	public void removeFromTrash(Note note);
	
	//public Label createLabel(User user, Label label);
	
	//public List<Label> getLabels(User user);

}
