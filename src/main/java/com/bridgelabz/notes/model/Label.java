package com.bridgelabz.notes.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@EqualsAndHashCode
@Table(name = "label")
public class Label {
	
	@Id
	@GeneratedValue(generator = "sequence", strategy = GenerationType.IDENTITY)
	private Long labelId;
	
	@NotNull
	@Column(name="label_name",unique = true)
	
	private String labelName;
	
	@NotNull
	@Column(name="user_id")
    private Long userId;
	
	@Column(name="note_id")
	private Long noteId;
	
	@JsonIgnore
	@JoinColumn(name="user_id")
    @ManyToMany
    @JoinTable(name="note_label",joinColumns = {@JoinColumn(name="label_id")},inverseJoinColumns = {@JoinColumn(name="note_id")})
    private Set<Note> noteList;
	
}
