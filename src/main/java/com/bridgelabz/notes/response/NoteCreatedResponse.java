package com.bridgelabz.notes.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class NoteCreatedResponse 
{
	private Long id;
	
	private String name;
	
	private String title;

	@Override
	public String toString() {
		
		return "NoteCreatedResponse [id=" + id + ", name=" + name + ", title=" + title + "]"+"\n Note Created SuccessFully";
	}
	
}
