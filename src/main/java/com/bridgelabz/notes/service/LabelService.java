package com.bridgelabz.notes.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.notes.dao.ILabelDao;
import com.bridgelabz.notes.dao.INotesDao;
import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.LabelResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;
import com.bridgelabz.notes.util.UserData;

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
	@Autowired
	RestTemplate restTemplate;

	String response400 = "No Any Notes Available or Token Expired";

	@Override
	public ResponseEntity<LabelResponce> createLable(String token, Label label) {
		log.info("hdjifhdjifhdjkfhdjkfhd:----------------------------");
		label.setUserId(tokenUtil.parseToken(token));
		if (verifyUser(token) && labelDao.createLabel(label))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponce(201, "" + label.getLabelName() + " Label Created Successfully "));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabelResponce(400, response400));
	}

	@Override
	@Transactional
	public ResponseEntity<LabelResponce> deleteLabel(String token, Long labelId) {
		if (labelDao.deleteLabel(labelId) > 0 && verifyUser(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponce(202, "label deleted Successfully"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}

	@Override
	@Transactional
	public ResponseEntity<LabelResponce> editLabel(Long labelId, String newName, String token) {
		if (labelDao.updateLable(labelId, newName) > 0 && verifyUser(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201, "Label Updated"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
	}

	@Transactional
	// input note id,label id
	public ResponseEntity<LabelResponce> addLabel(String token, Long labelId, Long noteId) {
		try {
			log.error("Msg" + noteId);
			Note note = noteDao.findNoteById(noteId);
			Label labelObject = labelDao.getLableById(labelId);
			log.info("LAbel NAme---------" + labelObject.getLabelName());
			log.info("Label Id-----------" + labelObject.getLabelId());
			log.info("Label NoteId-----------" + labelObject.getNoteId());
			note.getLabelList().add(labelObject);

			if (verifyUser(token)) {

				if (labelDao.addLabel(labelObject, noteId) > 0) {
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201,
							labelObject.getLabelName() + "Label AddedSuccessfully to->" + labelObject.getNoteId()));
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
				}
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
		}
	}

	public ResponseEntity<LabelResponce> getAllLabels(String token) {
		if (verifyUser(token)) {
			Long userId = tokenUtil.parseToken(token);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponce(201, labelDao.getAllLabelByUserId(userId),
							"Number Of Labels Are: " + labelDao.getAllLabelByUserId(userId).size()));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
		}
	}

	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		UserData userData = restTemplate.getForObject("http://localhost:8086/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
				+ (userData.getId() == getUserIdFromToken(token)));
		return (userData.getId() == getUserIdFromToken(token));
	}

	public long getUserIdFromToken(String token) {
		return tokenUtil.parseToken(token);
	}

}
