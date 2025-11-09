package com.banking.system.service;

import com.banking.system.dto.CustomerDTO;
import com.banking.system.dto.LoginRequestDTO;
import com.banking.system.dto.LoginResponseDTO;
import com.banking.system.exception.CustomerNotFoundException;
import com.banking.system.exception.DuplicateResourceException;
import com.banking.system.exception.InvalidCredentialsException;
import com.banking.system.model.Customer;
import com.banking.system.model.Role;
import com.banking.system.repository.CustomerRepository;
import com.banking.system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	private final RoleRepository roleRepository;
	
	public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
		log.info("Registering new customer with email: {}", customerDTO.getEmail());
		
		if (customerRepository.existsByEmail(customerDTO.getEmail())) {
			throw new DuplicateResourceException("Customer", "email: " + customerDTO.getEmail());
		}
		
		if (customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())) {
			throw new DuplicateResourceException("Customer", "phone number: " + customerDTO.getPhoneNumber());
		}
		
		Customer customer = new Customer();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		customer.setAddress(customerDTO.getAddress());
		customer.setPassword(customerDTO.getPassword()); // In production, hash this
		customer.setActive(true);
		
		// Assign CUSTOMER role by default
		Role customerRole = roleRepository.findByName(Role.RoleType.CUSTOMER)
			.orElseGet(() -> {
				Role newRole = new Role();
				newRole.setName(Role.RoleType.CUSTOMER);
				return roleRepository.save(newRole);
			});
		
		Set<Role> roles = new HashSet<>();
		roles.add(customerRole);
		customer.setRoles(roles);
		
		Customer savedCustomer = customerRepository.save(customer);
		log.info("Customer registered successfully with ID: {}", savedCustomer.getId());
		
		return convertToDTO(savedCustomer);
	}
	
	@Transactional(readOnly = true)
	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
		return convertToDTO(customer);
	}
	
	@Transactional(readOnly = true)
	public CustomerDTO getCustomerByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email)
			.orElseThrow(() -> new CustomerNotFoundException(email));
		return convertToDTO(customer);
	}
	
	@Transactional(readOnly = true)
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
	
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		log.info("Updating customer with ID: {}", id);
		
		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
		
		if (customerDTO.getFirstName() != null) {
			customer.setFirstName(customerDTO.getFirstName());
		}
		if (customerDTO.getLastName() != null) {
			customer.setLastName(customerDTO.getLastName());
		}
		if (customerDTO.getPhoneNumber() != null) {
			if (!customer.getPhoneNumber().equals(customerDTO.getPhoneNumber()) &&
				customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())) {
				throw new DuplicateResourceException("Customer", "phone number: " + customerDTO.getPhoneNumber());
			}
			customer.setPhoneNumber(customerDTO.getPhoneNumber());
		}
		if (customerDTO.getAddress() != null) {
			customer.setAddress(customerDTO.getAddress());
		}
		if (customerDTO.getActive() != null) {
			customer.setActive(customerDTO.getActive());
		}
		
		Customer updatedCustomer = customerRepository.save(customer);
		log.info("Customer updated successfully with ID: {}", updatedCustomer.getId());
		
		return convertToDTO(updatedCustomer);
	}
	
	public void deleteCustomer(Long id) {
		log.info("Deleting customer with ID: {}", id);
		
		Customer customer = customerRepository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
		
		customer.setActive(false);
		customerRepository.save(customer);
		log.info("Customer deactivated successfully with ID: {}", id);
	}
	
	@Transactional(readOnly = true)
	public Customer getCustomerEntity(Long id) {
		return customerRepository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
	}
	
	@Transactional(readOnly = true)
	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		log.info("Login attempt for email: {}", loginRequest.getEmail());
		
		Customer customer = customerRepository.findByEmail(loginRequest.getEmail())
			.orElseThrow(() -> new InvalidCredentialsException());
		
		if (!customer.getActive()) {
			throw new RuntimeException("Account is inactive");
		}
		
		// Simple password comparison (in production, use BCrypt)
		if (!customer.getPassword().equals(loginRequest.getPassword())) {
			throw new InvalidCredentialsException();
		}
		
		// Generate simple token (in production, use JWT)
		String token = Base64.getEncoder().encodeToString(
			(customer.getId() + ":" + customer.getEmail()).getBytes()
		);
		
		CustomerDTO userDTO = convertToDTO(customer);
		
		log.info("Login successful for customer ID: {}", customer.getId());
		
		return new LoginResponseDTO(token, userDTO);
	}
	
	@Transactional(readOnly = true)
	public LoginResponseDTO adminLogin(LoginRequestDTO loginRequest) {
		log.info("Admin login attempt for email: {}", loginRequest.getEmail());
		
		Customer customer = customerRepository.findByEmail(loginRequest.getEmail())
			.orElseThrow(() -> new InvalidCredentialsException());
		
		if (!customer.getActive()) {
			throw new RuntimeException("Account is inactive");
		}
		
		// Check if user has ADMIN role
		boolean isAdmin = customer.getRoles().stream()
			.anyMatch(role -> role.getName() == Role.RoleType.ADMIN);
		
		if (!isAdmin) {
			throw new RuntimeException("Access denied. Admin privileges required.");
		}
		
		// Simple password comparison (in production, use BCrypt)
		if (!customer.getPassword().equals(loginRequest.getPassword())) {
			throw new InvalidCredentialsException();
		}
		
		// Generate simple token (in production, use JWT)
		String token = Base64.getEncoder().encodeToString(
			(customer.getId() + ":" + customer.getEmail() + ":admin").getBytes()
		);
		
		CustomerDTO userDTO = convertToDTO(customer);
		
		log.info("Admin login successful for customer ID: {}", customer.getId());
		
		return new LoginResponseDTO(token, userDTO);
	}
	
	private CustomerDTO convertToDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setFirstName(customer.getFirstName());
		dto.setLastName(customer.getLastName());
		dto.setEmail(customer.getEmail());
		dto.setPhoneNumber(customer.getPhoneNumber());
		dto.setAddress(customer.getAddress());
		dto.setActive(customer.getActive());
		dto.setCreatedAt(customer.getCreatedAt());
		dto.setUpdatedAt(customer.getUpdatedAt());
		
		if (customer.getRoles() != null) {
			Set<String> roleNames = customer.getRoles().stream()
				.map(role -> role.getName().name())
				.collect(Collectors.toSet());
			dto.setRoles(roleNames);
		}
		
		return dto;
	}
}

