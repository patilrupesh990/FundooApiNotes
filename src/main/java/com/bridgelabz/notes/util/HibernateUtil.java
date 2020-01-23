package com.bridgelabz.notes.util;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.model.Note;

import lombok.extern.slf4j.Slf4j;

@Component
@SuppressWarnings("unchecked")
@Slf4j
public class HibernateUtil<T> 
{
	@Autowired 
	private EntityManager entityManager;
	
	
	public T save(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.save(object);
	}
	
	public Query<T> createQuery(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	
	public void update(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		 session.update(object);
	}
	public T updateAndGetObject(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.createQuery(query).list().stream().findFirst();
	}
	
	public Query<T> select(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	public T getCurrentNote(Serializable value)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.get(Note.class,value);
	}
	public T getCurrentLable(Serializable value)
	{
		log.info("value:"+value);
		Session session = entityManager.unwrap(Session.class);
		return (T) session.get(Label.class,value);
	}
	
}
