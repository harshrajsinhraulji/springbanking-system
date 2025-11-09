package com.banking.system.service;

import com.banking.system.dto.CustomerDTO;
import com.banking.system.model.Customer;
import com.banking.system.model.Role;
import com.banking.system.repository.CustomerRepository;
import com.banking.system.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	private CustomerDTO customerDTO;
	private Customer customer;
	private Role customerRole;
	
	@BeforeEach
	void setUp() {
		customerDTO = new CustomerDTO();
		customerDTO.setFirstName("John");
		customerDTO.setLastName("Doe");
		customerDTO.setEmail("john.doe@example.com");
		customerDTO.setPhoneNumber("1234567890");
		customerDTO.setAddress("123 Main St");
		customerDTO.setPassword("password123");
		
		customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setEmail("john.doe@example.com");
		customer.setPhoneNumber("1234567890");
		customer.setAddress("123 Main St");
		customer.setPassword("password123");
		customer.setActive(true);
		
		customerRole = new Role();
		customerRole.setId(1L);
		customerRole.setName(Role.RoleType.CUSTOMER);
	}
	
	@Test
	void testRegisterCustomer_Success() {
		when(customerRepository.existsByEmail(anyString())).thenReturn(false);
		when(customerRepository.existsByPhoneNumber(anyString())).thenReturn(false);
		when(roleRepository.findByName(Role.RoleType.CUSTOMER)).thenReturn(Optional.of(customerRole));
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		
		CustomerDTO result = customerService.registerCustomer(customerDTO);
		
		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("john.doe@example.com", result.getEmail());
		verify(customerRepository, times(1)).save(any(Customer.class));
	}
	
	@Test
	void testRegisterCustomer_EmailExists() {
		when(customerRepository.existsByEmail(anyString())).thenReturn(true);
		
		assertThrows(RuntimeException.class, () -> {
			customerService.registerCustomer(customerDTO);
		});
		
		verify(customerRepository, never()).save(any(Customer.class));
	}
	
	@Test
	void testGetCustomerById_Success() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		
		CustomerDTO result = customerService.getCustomerById(1L);
		
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("John", result.getFirstName());
	}
	
	@Test
	void testGetCustomerById_NotFound() {
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			customerService.getCustomerById(1L);
		});
	}
}

