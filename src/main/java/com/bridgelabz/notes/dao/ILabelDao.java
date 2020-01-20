package com.bridgelabz.notes.dao;

import com.bridgelabz.notes.model.Label;

public interface ILabelDao {
	
	public int addLabel(Label label, Long userId);
	public int deleteLabel(Long noteId, Long labelId) ;
	public int updateLable(Long labelId, String newName) ;
}
