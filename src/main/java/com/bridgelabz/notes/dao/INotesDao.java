package com.bridgelabz.notes.dao;

import java.util.List;

import com.bridgelabz.notes.model.Note;


public interface INotesDao 
{
	
	
	public Note createNote(Long userId, Note note);
	
	public Note getNoteById(Long noteId);
	
	public Note updateNote(Long noteId, Note note, Long userId);
	
	public Integer deleteNote(Long uId, Long nId);
	
	public Integer pinUnpinNote(Long userId, Long noteId);
	
	public List<Note> getAllNoteList(Long uId);
	
	public Integer isTrash(Long noteId);	
	
	void archiveNote(Note note);
	
	List<Note> getTrashedNoteList(Long userId);
	
	
}
