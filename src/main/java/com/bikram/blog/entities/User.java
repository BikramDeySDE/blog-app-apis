package com.bikram.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(name = "user_name", nullable = false, length = 100)
	private String userName;
	
	private String userEmail;
	
	private String userPassword;
	
	private String userAbout;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mapped by 'user' of the Entity 'Post'
	private List<Post> posts = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)	// many to many => one user can have multiple roles, and one role can be assigned to multiple users
	@JoinTable(	name = "user_role",		// join table => this table will be used to map user and roles
				joinColumns = @JoinColumn(name="user", referencedColumnName = "userId"), // relationship will be mapped by the column 'userId' of the 'users' table // name of the column in 'user_role' table, where userIds will be stored, will be 'user'
				inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "roleId")) // relationship will be mapped by the column 'roleId' of the 'roles' table // name of the column in 'user_role' table, where roleIds will be stored, will be 'role'
	private Set<Role> roles = new HashSet<>();

	// grantedAuthorities : we are considering 'roles' as 'grantedAutorities'
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// converting each 'role' to 'authority'
		List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
		return authorities;
	}

	// password
	@Override
	public String getPassword() {
		return this.userPassword;
	}

	// username (userEmail)
	// we will return 'userEmail' as username because for authentication, we will be using 'userEmail' as username
	@Override
	public String getUsername() {
		return this.userEmail;
	}
	
}

/*
 * 
 * table : 'user_role' 
 * +++++++++++++++++++++++
 * +   user   +   role   +
 * +++++++++++++++++++++++
 * + (userId) + (roleId) +
 * + (userId) + (roleId) +
 * + (userId) + (roleId) +
 * +++++++++++++++++++++++
 * 
 * 
 */
