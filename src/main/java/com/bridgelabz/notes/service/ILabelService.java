package com.bridgelabz.notes.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.response.LabelResponce;

public interface ILabelService {
	
	public ResponseEntity<Object> createLable(String token, Label label); 
	public ResponseEntity<Object> addLabel(String token,Long labelId,Long noteId);
	
	public ResponseEntity<Object> deleteLabel(String token, Long labelId); 
	
	public ResponseEntity<Object> editLabel(Long labelId,String newName,String token);
	
	public ResponseEntity<Object> getAllLabels(String token); 
	public List<Object[]> getNotesByLabelId(String token, Long labelId) ;

	//public void editLabel();
	
	//public void getnotesByLabel();
	

}
