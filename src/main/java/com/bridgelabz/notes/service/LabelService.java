package com.bridgelabz.notes.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.notes.dao.ILabelDao;
import com.bridgelabz.notes.dao.INotesDao;
import com.bridgelabz.notes.exception.UserDoesNotExistException;
import com.bridgelabz.notes.model.Label;
import com.bridgelabz.notes.model.Note;
import com.bridgelabz.notes.response.LabelResponce;
import com.bridgelabz.notes.util.JwtTokenUtil;
import com.bridgelabz.notes.util.LabelData;
import com.bridgelabz.notes.util.NoteData;
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
	String userNotExist = "User Token Expired or User Does Note Exist";

	@Override
	public ResponseEntity<Object> createLable(String token, Label label) {
		log.info("User Id from token Create label Service----------------------------" + tokenUtil.parseToken(token));
		label.setUserId(tokenUtil.parseToken(token));
		if (verifyUser(token) && labelDao.createLabel(label))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponce(201, "" + label.getLabelName() + " Label Created"));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDoesNotExistException(userNotExist, 400));
	}

	@Override
	@Transactional
	public ResponseEntity<Object> deleteLabel(String token, Long labelId) {
		if (labelDao.deleteLabel(labelId) > 0 && verifyUser(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(202, "label deleted"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserDoesNotExistException(userNotExist, 400));
	}

	@Override
	@Transactional
	public ResponseEntity<Object> editLabel(Long labelId, String newName, String token) {
		if (labelDao.updateLable(labelId, newName) > 0 && verifyUser(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201, "Label Updated"));
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserDoesNotExistException(userNotExist, 400));
	}

	@Override
	@Transactional
	// input note id,label id
	public ResponseEntity<Object> addLabel(String token, Long labelId, Long noteId) {
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
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponce(201, " Label Added"));
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
				}
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserDoesNotExistException(userNotExist, 400));
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new LabelResponce(400, response400));
		}
	}

	@Override
	public ResponseEntity<Object> getAllLabels(String token) {
		if (verifyUser(token)) {
			Long userId = tokenUtil.parseToken(token);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponce(201, labelDao.getAllLabelByUserId(userId),
							"Number Of Labels Are: " + labelDao.getAllLabelByUserId(userId).size()));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserDoesNotExistException(userNotExist, 400));
		}
	}

	public List<NoteData[]> getNotesByLabelId(String token, Long labelId) {
		if (verifyUser(token)) {

			return labelDao.getNotesByLabelId(labelId);

		} else {
			return null;

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<LabelData[]> getAllLabelofNote(String token, Long noteId) {
		if (verifyUser(token)) {
			List<LabelData[]> labels = new ArrayList<LabelData[]>();
		BeanUtils.copyProperties(labelDao.getAllLabelofNote(noteId), labels);
			return labels;

		} else {
			return  null;
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
