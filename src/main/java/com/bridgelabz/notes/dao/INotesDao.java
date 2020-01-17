package com.bridgelabz.notes.dao;

import java.util.List;

import com.bridgelabz.notes.model.Note;


public interface INotesDao 
{
	
	
	public Note createNote(Long userId, Note note);
	
	public Note getNoteById(Long noteId);
	
	public Note updateNote(Long noteId, Note note, Long userId);
	
	public Integer deleteNote(Long uId, Long nId);
	
	public List<Note> getNoteList(Integer uId, String noteCategory);
	
	public List<Note> getAllNoteList(Long uId);
	
	public void moveToTrash(Integer nId);
	
	List<Note> getTrashedNoteList(Integer uId);
	
	void archiveNote(Note note);
	
	
}
