package com.bharat.bootiful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@SpringBootApplication
public class BootifulApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootifulApplication.class, args);
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener (CustomerService customerService) {
		return event ->
			customerService.getAllCustomers().forEach(System.out::println);
	}
	@Controller
	@ResponseBody
	class CustomerHttpController {

		private final CustomerService customerService;

		public CustomerHttpController(CustomerService customerService) {
			this.customerService = customerService;
		}
		@GetMapping("/customers")
		Collection<Customer> getAllCustomers() {
			return this.customerService.getAllCustomers();
		}
		@GetMapping("/customer/{id}")
		Customer getCustomerById(@PathVariable Integer id) {
			return this.customerService.getCustomerById(id);
		}
	}
	@Service
	class CustomerService {
		private final JdbcTemplate jdbcTemplate;
		private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
			@Override
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Customer(rs.getInt("id"), rs.getString("name"));
			}
		};

		public CustomerService(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		Collection<Customer> getAllCustomers() {
			return this.jdbcTemplate.query("select * from customer", this.customerRowMapper);
		}

		Customer getCustomerById (Integer id) {
			return this.jdbcTemplate.queryForObject("select * from customer where id=?", this.customerRowMapper, id);
		}
	}

	record Customer (Integer id, String name) {

	}
}
