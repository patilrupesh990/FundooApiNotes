package com.bridgelabz.notes.dao;

import java.util.List;

import com.bridgelabz.notes.model.Label;

public interface ILabelDao {
	
	public int addLabel(Label label,Long noteId);
	public boolean createLabel(Label label);
	public int deleteLabel( Long labelId) ;
	public int updateLable(Long labelId, String newName) ;
	public Label getLableById(Long labelId);
	public List<Label> getAllLabelByUserId(Long userId);
	public List<Object[]> getNotesByLabelId(Long labelId);

}
