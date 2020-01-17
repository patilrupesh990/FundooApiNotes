package com.bridgelabz.notes.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.Note;

import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class NotesDaoimpl implements INotesDao{
	
	@Autowired 
	private EntityManager entityManager;
	
	@Override
	public Note createNote(Long userId, Note note) {
		Session session = entityManager.unwrap(Session.class);
		note.setUserId(userId);
		session.save(note);
		log.info("New Note Created..");
		return note;
	}
	
	@Override
	public Note getNoteById(Long noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.get(Note.class, noteId);
	}

	@Override
	public Note updateNote(Long noteId, Note note, Long userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Note noteObj = currentSession.get(Note.class, noteId);
		if (noteObj != null) {
			note.setUserId(userId);
			currentSession.update(note);
			return note;
		}
		return noteObj;
	}

	@Override
	public Integer deleteNote(Long userId, Long noteId) {
		Session session = entityManager.unwrap(Session.class);
		@SuppressWarnings("unchecked")
		Query<Note> query=session.createQuery("DELETE FROM Note WHERE id = :noteid AND userId =:userid");
		query.setParameter("noteid", noteId);
		query.setParameter("userid", userId);
		return query.executeUpdate();
	}
	
	@Override
	public List<Note> getAllNoteList(Long userId) {
		Session session=entityManager.unwrap(Session.class);
		@SuppressWarnings("unchecked")
		Query<Note> query=session.createQuery("from Note where userId=:userid");
		query.setParameter("userid", userId);
		
		return query.list();
	}
	
	

	@Override
	public List<Note> getNoteList(Integer uId, String noteCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void moveToTrash(Integer nId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Note> getTrashedNoteList(Integer uId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void archiveNote(Note note) {
		// TODO Auto-generated method stub
		
	}
	
	public  Session getSession()
	{
		return entityManager.unwrap(Session.class);
	}
	

}
