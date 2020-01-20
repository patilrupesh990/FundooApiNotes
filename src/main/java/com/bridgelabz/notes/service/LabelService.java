package com.bridgelabz.notes.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dao.ILabelDao;
import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.response.LabelResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelService implements ILabelService{

	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	ILabelDao labelDao;
	String response400="Not Available or Token Expired";

	@Override
	@Transactional
	public ResponseEntity<LabelResponce> addLable(String token, Label label) {

		
		if (labelDao.addLabel(label, tokenUtil.parseToken(token)) > 0 && verifyToken(token))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponce(201, "" + label.getLabelName() + " Label Added to ->" + label.getNoteId()));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new LabelResponce(400, response400));
	}
	@Override
	@Transactional
	public ResponseEntity<LabelResponce> deleteLabel(String token, Long labelId, Long noteId) {
		if (labelDao.deleteLabel(noteId, labelId) > 0 && verifyToken(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponce(202, "label deleted Successfully"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}
	@Override
	@Transactional
	public ResponseEntity<LabelResponce> editLabel(Long labelId,String newName,String token)
	{
		if(labelDao.updateLable(labelId, newName)>0 && verifyToken(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201,"Label Updated"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
			
	}
	@Override
	public boolean verifyToken(String token)
	{
		try {
			tokenUtil.parseToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}

}
