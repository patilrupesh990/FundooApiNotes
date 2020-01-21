package com.bridgelabz.notes.service;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.response.LabelResponce;

public interface ILabelService {
	
	public ResponseEntity<LabelResponce> createLable(String token, Label label); 
	public ResponseEntity<LabelResponce> addLabel(String token,Long labelId,Long noteId);
	
	public ResponseEntity<LabelResponce> deleteLabel(String token, Long labelId); 
	
	public ResponseEntity<LabelResponce> editLabel(Long labelId,String newName,String token);
	
	public boolean verifyToken(String token);
	public ResponseEntity<LabelResponce> getAllLabels(String token); 

	//public void editLabel();
	
	//public void getnotesByLabel();
	

}
