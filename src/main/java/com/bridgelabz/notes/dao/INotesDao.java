package com.bridgelabz.notes.dao;

import java.util.List;

import com.bridgelabz.notes.model.Note;


public interface INotesDao 
{
	
	
	public Note createNote(Long userId, Note note);
	public void updateNote(Long noteId, Note note, Long userId);
	public Integer deleteNote(Long uId, Long nId);
	
	
	
	public Integer pinUnpinNote(Long userId, Long noteId);
	public Integer trashedNote(Long noteId);	
	public Integer archivedNote(Long noteId);
	
	public Note getNoteById(Long noteId);

	public List<Note> getAllNoteList(Long uId);
	public List<Note> getTrashedNoteList(Long userId);
	public List<Note> getArchiveNoteList(Long userId);
	public Note findNoteById(Long noteId);
	
	
}
