package com.bridgelabz.notes.dao;

import java.util.List;

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

		String query = "DELETE FROM Note WHERE id = :noteid AND userId =:useri";
		Query<Note> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("noteid", noteId);
		hQuery.setParameter("useri", userId);
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
			noteObject.setPin(!noteObject.isPin());
			hibernateUtil.update(noteObject);
			if(noteObject.isPin())
				return 1;
			else
				return 2;
		}
		return 0;
	}
	@Override
	public Integer trashedNote(Long noteId) {
		Note noteObject = hibernateUtil.getCurrentNote(noteId);
		if (noteObject != null) {
			noteObject.setTrash(!noteObject.isTrash());
			hibernateUtil.update(noteObject);
			if(noteObject.isTrash())
				return 1;
			else
				return 2;
		}
		return 0;
	}

	@Override
	public List<Note> getTrashedNoteList(Long userId) {
		String query = "FROM Note WHERE isTrash is true AND userId=:userid";
		Query<Note> hquery = hibernateUtil.select(query);
		hquery.setParameter("userid", userId);
		return hquery.list();
	}

	@Override
	public Integer archivedNote(Long noteId) {
		Note noteobject = hibernateUtil.getCurrentNote(noteId);
		if (noteobject != null) {
			noteobject.setArchive(!noteobject.isArchive());
			hibernateUtil.update(noteobject);
			if (noteobject.isArchive())
				return 1;
			else
				return 2;
		} else {
			return 0;
		}

	}

	@Override
	public List<Note> getArchiveNoteList(Long userId) {
		String query = "FROM Note WHERE isArchive is true AND userId=:userid";
		Query<Note> hquery = hibernateUtil.select(query);
		hquery.setParameter("userid", userId);
		return hquery.list();
	}
	
	public List<Note> getAllNotesByLabel(String noteID,String labelID)
	{
	//	String query="FROM Note WHER"
		return null;
		
	}
	//dont toch
	@Override
	public Note findNoteById(Long noteId)
	{
		String query="FROM Note WHERE id =:noteId";
		Query<Note> hquery = hibernateUtil.select(query);
		hquery.setParameter("noteId", noteId);
		return hquery.getSingleResult();
	}

}
