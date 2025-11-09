package com.banking.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "First name is required")
	@Column(nullable = false, length = 100)
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	@Column(nullable = false, length = 100)
	private String lastName;
	
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is required")
	@Column(unique = true, nullable = false, length = 255)
	private String email;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be 10-15 digits")
	@Column(nullable = false, length = 15)
	private String phoneNumber;
	
	@NotBlank(message = "Address is required")
	@Column(nullable = false, length = 500)
	private String address;
	
	@Column(nullable = false, length = 100)
	private String password; // In production, this should be hashed
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "customer_roles",
		joinColumns = @JoinColumn(name = "customer_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	
	@Column(nullable = false)
	private Boolean active = true;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}

