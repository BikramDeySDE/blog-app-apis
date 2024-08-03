package com.bikram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
