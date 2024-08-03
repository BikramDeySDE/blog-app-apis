package com.bikram.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bikram.blog.config.AppConstants;
import com.bikram.blog.entities.Role;
import com.bikram.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{ // implemented the interface 'CommandLineRunner' to encode a particular password

	// for the purpose of encoding a particular password
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	// declaring bean for modelMapper : it will create ModelMapper object automatically whenever we'll use auto-wire (@Autowired) for this modelMapper 
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
	// This method is coming due to implement the interface 'CommandLineRunner' | whenever the application will run, this method will also be running | 
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("$Hey_&")); // "$Hey_&" is the password which we can encode | we will get the encoded password on the console, whenever we'll run the application
	
		// whenever the application will run, it will check if roles defined below are created or not, if not create, the roles will be created, and will be saved in DB
		Role role1 = new Role();
		role1.setRoleId(AppConstants.ADMIN_USER_ROLE_ID);
		role1.setRoleName("ROLE_ADMIN");
		
		Role role2 = new Role();
		role2.setRoleId(AppConstants.NORMAL_USER_ROLE_ID);
		role2.setRoleName("ROLE_NORMAL");
		
		List<Role> roles = List.of(role1,role2);
		
		List<Role> savedRoles = this.roleRepository.saveAll(roles); // saving roles in DB
		
		savedRoles.forEach(r -> {
			System.out.println(r.getRoleName()); // printing each saved roles on console
		});
		
	}

}
