package com.bridgelabz.notes.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class LabelDaoimpl implements ILabelDao {
	@Autowired
	HibernateUtil<Label> hibernateUtil;
	@Override
	public int addLabel(Label label, Long noteId) {
		
		String query = "UPDATE Label SET noteId = :NOTEID WHERE labelId= :LABELID";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("NOTEID", noteId);
		hQuery.setParameter("LABELID",label.getLabelId());
		return hQuery.executeUpdate();
	}
	@Override
	public boolean createLabel(Label label) {
		try {

			hibernateUtil.save(label);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public int deleteLabel( Long labelId) {
		String query = "DELETE FROM Label WHERE labelId = :label";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("label", labelId);
		return hQuery.executeUpdate();
	}
	@Override
	public int updateLable(Long labelId, String newName) {
		Label labelObject = hibernateUtil.getCurrentLable(labelId);
		if (labelObject != null) {
			labelObject.setLabelName(newName);
			hibernateUtil.update(labelObject);
			return 1;
		}
		return 0;
	}
	@Override
	public Label getLableById(Long labelId) {
		return hibernateUtil.getCurrentLable(labelId);
	}
	
	@Override
	public List<Label> getAllLabelByUserId(Long userId)
	{
		String query = "FROM Label WHERE userId = :user";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("user", userId);
		return hQuery.getResultList();
	}
	
	public List<Object[]> getNotesByLabelId(Long labelId)
	{
		String SQL = "select * from notes left join note_label on note_label.note_id=notes.id where note_label.label_id = :id"; 
				Query query =hibernateUtil.createNativeQuery(SQL);
				query.setParameter("id", labelId);
				return query.list();
				//no entity mapping
	}



}
