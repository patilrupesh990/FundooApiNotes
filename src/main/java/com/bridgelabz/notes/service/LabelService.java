package com.bridgelabz.notes.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bridgelabz.notes.dao.ILabelDao;
import com.bridgelabz.notes.dao.INotesDao;
import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.LabelResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelService implements ILabelService {

	@Autowired
	JwtTokenUtil tokenUtil;
	@Autowired
	ILabelDao labelDao;
	@Autowired
	INotesDao noteDao;
	String response400 = "Not Available or Token Expired";

	@Override
	public ResponseEntity<LabelResponce> createLable(String token, Label label) {
		log.info("hdjifhdjifhdjkfhdjkfhd:----------------------------");
		label.setUserId(tokenUtil.parseToken(token));
		if (verifyToken(token) && labelDao.createLabel(label))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponce(201, "" + label.getLabelName() + " Label Created Successfully "));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabelResponce(400, response400));
	}

	@Override
	@Transactional
	public ResponseEntity<LabelResponce> deleteLabel(String token, Long labelId) {
		if (labelDao.deleteLabel(labelId) > 0 && verifyToken(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponce(202, "label deleted Successfully"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}

	@Override
	@Transactional
	public ResponseEntity<LabelResponce> editLabel(Long labelId, String newName, String token) {
		if (labelDao.updateLable(labelId, newName) > 0 && verifyToken(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201, "Label Updated"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}

	@Transactional
	// input note id,label id
	public ResponseEntity<LabelResponce> addLabel(String token, Long labelId, Long noteId) {

		log.error("Msg"+noteId);
		Note note = noteDao.findNoteById(noteId);
		Label labelObject = labelDao.getLableById(labelId);
		log.info("LAbel NAme---------"+labelObject.getLabelName());
		log.info("Label Id-----------"+labelObject.getLabelId());
		log.info("Label NoteId-----------"+labelObject.getNoteId());
		note.getLabelList().add(labelObject);

		if (note != null && verifyToken(token) && labelObject != null) {

			if (labelDao.addLabel(labelObject, noteId) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201,
						labelObject.getLabelName() + "Label AddedSuccessfully to->" + labelObject.getNoteId()));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
			}
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}

	public ResponseEntity<LabelResponce> getAllLabels(String token) {
		if (labelDao.verifyUser(tokenUtil.parseToken(token))) {
			Long userId = tokenUtil.parseToken(token);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201, labelDao.getAllLabelByUserId(userId),
					"Number Of Labels Are: " + labelDao.getAllLabelByUserId(userId).size()));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
		}
	}

	
	@Override
	public boolean verifyToken(String token) {
		try {
			log.info("incoming value first check:" + tokenUtil.parseToken(token));
			if (labelDao.verifyUser(tokenUtil.parseToken(token))) {
				return true;

			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
