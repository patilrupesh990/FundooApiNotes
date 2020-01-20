package com.bridgelabz.notes.service;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.response.LabelResponce;

public interface ILabelService {
	
	public ResponseEntity<LabelResponce> addLable(String token, Label label) ;
	
	public ResponseEntity<LabelResponce> deleteLabel(String token, Long labelId, Long noteId); 
	
	public ResponseEntity<LabelResponce> editLabel(Long labelId,String newName,String token);
	
	public boolean verifyToken(String token);

	//public void editLabel();
	
	//public void getnotesByLabel();
	

}
