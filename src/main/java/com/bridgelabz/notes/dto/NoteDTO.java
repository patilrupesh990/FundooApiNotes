package com.bridgelabz.notes.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;
@Data	
public class NoteDTO {
private Long id;
	
	
	private String name;	
	
	
	private String title;	
	
	private String description;
	
	private boolean isTrash;//trash
	
	private boolean isPin;//pin
	
	private boolean isArchive;
	
	private String color;
	
	private LocalDateTime reminder;
	
	
	private Timestamp createdTime;
	
	
	private Timestamp updaTimestamp;	
	
		
	private Long userId;
	
}
