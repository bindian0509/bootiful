package com.bharat.bootiful;
/*
 * @author bharat.verma
 * @created Saturday, 01 April 2023
 */

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

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
    Customer getCustomerByName (String name) {
        return this.jdbcTemplate.queryForObject("select * from customer where name=?", this.customerRowMapper, name);
    }
}