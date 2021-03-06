package com.bridgelabz.notes.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@EqualsAndHashCode
@Table(name = "notes")
public class Note {
	@Id
	@GeneratedValue(generator = "sequence", strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column(name="name") 
	private String name;
	
	@Column(name="title") 
	private String title;
	
	@Column(name="description") 
	private String description;
	
	@Column(name="isTrashed") 
	private boolean isTrash;//trash
	
	@Column(name="isPinned") 
	private boolean isPin;//pin
	
	@Column(name="isArchive") 
	private boolean isArchive;
	
	@Column(name="color") 
	private String color;
	
	@Column(name="reminder") 
	private LocalDateTime reminder;
	
	@Column(name="created_time")
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private Timestamp createdTime;
	
	@Column(name="last_update")
	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private Timestamp updaTimestamp;	
	
	@Column(name="user_id")
	private Long userId;
	
	@JoinColumn(name="user_id")
	@JsonIgnore
    @ManyToMany
    @JoinTable(name="note_label",joinColumns = {@JoinColumn(name="note_id")},inverseJoinColumns = {@JoinColumn(name="label_id")})
	private Set<Label> labelList;
	
}
