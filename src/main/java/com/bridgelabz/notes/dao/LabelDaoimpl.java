package com.bridgelabz.notes.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class LabelDaoimpl implements ILabelDao {
	@Autowired
	HibernateUtil<Label> hibernateUtil;

	public int addLabel(Label label, Long noteId) {
		System.out.println(noteId);
		
		String query = "UPDATE Label SET noteId = :NOTEID WHERE labelId= :LABELID";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("NOTEID", noteId);
		hQuery.setParameter("LABELID",label.getLabelId());
		return hQuery.executeUpdate();
	}

	public boolean createLabel(Label label) {
		try {

			hibernateUtil.save(label);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int deleteLabel( Long labelId) {
		String query = "DELETE FROM Label WHERE labelId = :label";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("label", labelId);
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

	public Label getLableById(Long labelId) {
		return hibernateUtil.getCurrentLable(labelId);
	}
	
	public List<Label> getAllLabelByUserId(Long userId)
	{
		String query = "FROM Label WHERE userId = :user";
		Query<Label> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("user", userId);
		return hQuery.getResultList();
	}

	public boolean verifyUser(Long userId) {
		log.info("Verify user Dao UserId:"+userId);
		log.info("true/false:"+hibernateUtil.getUserById(userId));
		if (hibernateUtil.getUserById(userId)) {
			log.info("Verify user Dao inside if");
			return true;
		} else {
			return false;
		}

	}

	public void getAllNotesByLabelName(String labelName) {

	}

}
