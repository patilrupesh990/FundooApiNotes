package com.bridgelabz.notes.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.notes.model.Label;

import lombok.Data;

@Data
@Component
@SuppressWarnings("unused")
public class LabelResponce {

	private int status;
	private List<Label> list;
	private String response;

	public LabelResponce(int status, List<Label> list, String response) {
		super();
		this.list = list;
		this.response = response;
		this.status = status;
	}

	public LabelResponce(int status, String response) {
		super();
		this.status = status;
		this.response = response;
	}
	public LabelResponce() {
	}

}
