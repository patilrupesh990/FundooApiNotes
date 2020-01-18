package com.bridgelabz.notes.dao;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class NotesDaoimpl implements INotesDao {

	@Autowired
	HibernateUtil<Note> hibernateUtil;

	@Override
	public Note createNote(Long userId, Note note) {
		note.setUserId(userId);
		hibernateUtil.save(note);
		log.info("New Note Created..");
		return note;
	}

	@Override
	public Note getNoteById(Long noteId) {

		String query = "FROM Note WHERE id =:noteId";
		Query<Note> hquery = hibernateUtil.select(query);
		hquery.setParameter("noteId", noteId);
		return hquery.getSingleResult();
	}

	@Override
	public Note updateNote(Long noteId, Note note, Long userId) {
			note.setUserId(userId);
			hibernateUtil.update(note);
			return note;
	}

	@Override
	public Integer deleteNote(Long userId, Long noteId) {

		String query = "DELETE FROM Note WHERE id = :noteid AND userId =:userid";
		Query<Note> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("noteid", noteId);
		hQuery.setParameter("userid", userId);
		return hQuery.executeUpdate();
	}

	@Override
	public List<Note> getAllNoteList(Long userId) {
		String query = "FROM Note WHERE userId =:userid";
		Query<Note> hquery = hibernateUtil.createQuery(query);
		hquery.setParameter("userid", userId);
		return hquery.list();
	}

	@Override
	public Integer pinUnpinNote(Long userId, Long noteId) {
		Note noteObject = hibernateUtil.getCurrentNote(noteId);
		if (noteObject != null) {
			noteObject.setPinned(!noteObject.isPinned());
			hibernateUtil.update(noteObject);
			return 1;
		}
		return 0;
	}

	public Integer isTrash(Long noteId) {
		Note noteObject = hibernateUtil.getCurrentNote(noteId);
		if (noteObject != null) {
			noteObject.setTrashed(!noteObject.isTrashed());
			hibernateUtil.update(noteObject);
			return 1;
		}
		return 0;
	}
	
	public List<Note> getNoteList(Integer userId, String noteCategory) {
			
		return null;
	}
	@Override
	public List<Note> getTrashedNoteList(Long userId) {
		String query = "FROM Note WHERE isTrashed is true AND userId=:userid";
		Query<Note> hquery = hibernateUtil.select(query);
		hquery.setParameter("userid", userId);
		return hquery.list();
	}

	@Override
	public void archiveNote(Note note) {

	}

	public Session getSession() {
		return null;
	}

}
