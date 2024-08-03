package com.bikram.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

	@Id // role-Id will not be auto-generated, it will be defined manually in code in the class "BlogApplication.java" (i.e. main class)
	private Integer roleId;
	private String roleName;
	
}
