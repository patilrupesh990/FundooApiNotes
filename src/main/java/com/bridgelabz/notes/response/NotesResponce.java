package com.bridgelabz.notes.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.notes.model.Note;

import lombok.Data;

@Data
@Component
public class NotesResponce {
	
		
		private int status;
		private String response;
		private List<Note> list; 
public NotesResponce(int status, String response) {
			super();
			this.status = status;
			this.response = response;
		}
		public NotesResponce(int status, String response, Object object) {
			super();
			this.status = status;
			this.response = response;
		}
		public NotesResponce(int status, String response, List<Note> list) {
			super();
			this.status = status;
			this.response = response;

				
			}
		public NotesResponce() {}
}
