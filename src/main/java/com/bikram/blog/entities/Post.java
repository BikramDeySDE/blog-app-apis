package com.bikram.blog.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "title", length = 100, nullable = false)
	private String postTitle;
	
	@Column(name = "description", length = 10000, nullable = false)
	private String postDescription;
	
	private String postImageName;
	
	private Date postAddedDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")	// column name would be "user_id"
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id")	// column name would be "category_id"
	private Category category;
	
}
