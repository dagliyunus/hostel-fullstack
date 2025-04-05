package com.hostel.hostelmanagementsystem.controller.admin;

import com.hostel.hostelmanagementsystem.dto.AdminCustomerDTO;
import com.hostel.hostelmanagementsystem.model.Customer;
import com.hostel.hostelmanagementsystem.service.admin.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/manageCustomer")
public class AdminCustomerController {

    private final CustomerService customerService;

    @Autowired
    public AdminCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves a customer by their unique customer ID.
     * <p>
     * Purpose: This endpoint is designed for admin users to fetch specific customer information
     * based on the customer's system-generated unique identifier. This can be useful in detail
     * views or editing workflows in the admin dashboard.
     * <p>
     * Request Format (Postman):
     * GET /api/customers/findById?customer_id=CUSTOMER_ID
     * <p>
     * Flow:
     * 1. Accepts the customer_id as a query parameter via @RequestParam.
     * 2. Delegates the call to customerService.findById(customerId).
     * 3. If a customer is found, returns HTTP 200 with the Customer object.
     * 4. If no customer matches the ID, returns HTTP 404 with a descriptive message.
     *
     * @param customerId the unique ID of the customer to retrieve
     * @return ResponseEntity containing the Customer or a 404 error message
     */
    @GetMapping("/findById")
    public ResponseEntity<?> getCustomerById(@RequestParam("customer_id") String customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }else{
            return ResponseEntity
                    .status(404)
                    .body("Customer with id " + customerId + " not found");
        }
    }

    /**
     * Retrieves a customer by their email address.
     * <p>
     * Purpose: This method is used by the admin to fetch customer details
     * using the customer's email. It is helpful when displaying or managing specific
     * customer records from the admin dashboard.
     * <p>
     * Request Format(Postman):
     * GET /api/customers/byemail?email=EMAIL_ADDRESS
     * <p>
     * Flow:
     * 1. Accepts a query parameter email from the request.
     * 2. Delegates to the service layer via customerService.getCustomerByEmail()
     * 3. If a matching customer is found, returns HTTP 200 with the data.
     * 4. If no customer is found, returns HTTP 404 with an error message.
     *
     * @param email the email address of the customer to look up
     * @return ResponseEntity containing the customer or a not found message
     */
    @GetMapping("/byemail")
    public ResponseEntity<?> getCustomerByEmail(@RequestParam("email") String email){
        Optional<Customer>  customer = customerService.getCustomerByEmail(email);
        if(customer.isPresent()){
            return ResponseEntity.ok(customer.get());
        }else {
            return ResponseEntity.
                    status(404)
                    .body("Customer with email" + email + " not found.");
        }
    }

    /**
     * Retrieves a list of all customers with detailed information including room number.
     * <p>
     * Purpose: This method is used to populate the admin dashboard's customer management section
     * with all registered customer records, mapped into AdminCustomerDTOs for clean frontend use.
     * <p>
     * Request Format (Postman):
     * GET /api/customers/findAll
     * <p>
     * Why isn't @RequestParam used?
     * This endpoint does not require any client input. It simply returns all customer records.
     * @RequestParam is only needed when specific data is requested via parameters.
     * <p>
     * Why use !isEmpty() instead of isPresent()?
     * The method returns a List, not an Optional. Therefore, we use !customerDTOList.isEmpty()
     * to check whether the list contains any data before returning it.
     * <p>
     * Flow:
     * 1. Calls customerService.getAllCustomers() which returns a list of AdminCustomerDTOs.
     * 2. Checks if the list is empty or not.
     * 3. Returns the list with HTTP 200 if not empty, else responds with HTTP 404.
     *
     * @return ResponseEntity containing a list of AdminCustomerDTOs or a 404 error
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<AdminCustomerDTO>> getAllCustomers(){
        List<AdminCustomerDTO> customerDTOList = customerService.getAllCustomers();
       return ResponseEntity.ok(customerDTOList);
    }

    /**
     * Creates a new customer in the system.
     * <p>
     * Purpose: This method is used by the admin to add a new customer to the system.
     * It enables the management of user data directly from the admin dashboard.
     * <p>
     * Request Format (Postman):
     * POST /api/customers/createCustomer
     * Body: JSON representation of AdminCustomerDTO
     * <p>
     * Flow:
     * 1. Accepts a customer DTO from the request body.
     * 2. Delegates the creation logic to customerService.createCustomer().
     * 3. If the customer is successfully created, returns HTTP 200 with the customer DTO.
     * 4. If the input is invalid, returns HTTP 400 with an error message.
     * 5. If an unexpected error occurs, returns HTTP 500 with a generic error message.
     *
     * @param dto the AdminCustomerDTO object containing customer data to be created
     * @return ResponseEntity containing the created customer or an error message
     */
    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody AdminCustomerDTO dto) {
        try {
            AdminCustomerDTO responseDto = customerService.createCustomer(dto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Customer could not be created due to internal error.");
        }
    }

    /**
     * Updates an existing customer in the system.
     * <p>
     * Purpose: This method is used by the admin to update customer information.
     * It facilitates editing existing records from the admin dashboard.
     * <p>
     * Request Format (Postman):
     * PUT /api/customers/updateCustomer
     * Body: JSON representation of AdminCustomerDTO
     * <p>
     * Flow:
     * 1. Accepts a customer DTO from the request body.
     * 2. Delegates the update logic to customerService.updateCustomer().
     * 3. Converts the updated entity back to a DTO via customerService.mapToDTO().
     * 4. If the customer is successfully updated, returns HTTP 200 with the updated DTO.
     * 5. If the input is invalid, returns HTTP 400 with an error message.
     * 6. If an unexpected error occurs, returns HTTP 500 with a generic error message.
     *
     * @param dto the AdminCustomerDTO object containing updated customer data
     * @return ResponseEntity containing the updated customer or an error message
     */
    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestBody AdminCustomerDTO dto) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(dto);
            AdminCustomerDTO responseDto = customerService.mapToDTO(updatedCustomer);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Customer could not be updated due to internal error.");
        }
    }

    /**
     * Deletes an existing customer from the system.
     * <p>
     * Purpose: This method is used by the admin to delete a customer record.
     * It enables customer removal functionality from the admin dashboard.
     * <p>
     * Request Format (Postman):
     * DELETE /api/customers/deleteCustomer?customer_id={customerId}
     * <p>
     * Flow:
     * 1. Accepts a customer ID from the request parameter.
     * 2. Delegates the delete logic to customerService.deleteCustomer().
     * 3. If the customer is successfully deleted, returns HTTP 200 with a success message.
     * 4. If the customer does not exist or the ID is invalid, returns HTTP 404 with an error message.
     * 5. If an unexpected error occurs, returns HTTP 500 with a generic error message.
     *
     * @param customerId the ID of the customer to be deleted
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestParam("customer_id") String customerId) {
        try{
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer deleted successfully.");
        }catch (IllegalArgumentException e){
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }
}
