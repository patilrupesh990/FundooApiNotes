package com.bridgelabz.notes.dao;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class LabelDaoimpl implements ILabelDao{
	@Autowired
	HibernateUtil<Label> hibernateUtil;

	public int addLabel(Label label, Long userId) {
		try {
			label.setUserId(userId);
			hibernateUtil.save(label);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int deleteLabel(Long noteId, Long labelId) {
		String query = "DELETE FROM Label WHERE labelId = :label AND noteId =:note";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("label", labelId);
		hQuery.setParameter("note", noteId);
		return hQuery.executeUpdate();
	}

	public int updateLable(Long labelId, String newName) {
		Label labelObject = hibernateUtil.getCurrentLable(labelId);
		if (labelObject != null) {
			labelObject.setLabelName(newName);
			hibernateUtil.update(labelObject);
			return 1;
		}
		return 0;
	}

	public void getAllNotesByLabelName(String labelName) {
		
	}

}
