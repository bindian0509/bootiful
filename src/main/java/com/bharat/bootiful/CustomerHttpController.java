package com.bharat.bootiful;
/*
 * @author bharat.verma
 * @created Saturday, 01 April 2023
 */

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

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

    @GetMapping("/customers/{name}")
    Customer getCustomerByName(@PathVariable String name) {
        Assert.state(Character.isUpperCase(name.charAt(0)), "First character of name starts with CAPITAL letter.");
        return this.customerService.getCustomerByName(name);
    }
}