package com.bridgelabz.notes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	
	@NotNull
	@Column(name="note_id")
	private Long noteId;
	
	@JoinColumn(name="user_id")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="note_label",joinColumns = {@JoinColumn(name="label_id")},inverseJoinColumns = {@JoinColumn(name="note_id")})
    private List<Note> noteList;
	
}
