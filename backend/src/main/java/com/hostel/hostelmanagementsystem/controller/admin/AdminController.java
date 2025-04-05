// src/main/java/com/hostel/hostelmanagementsystem/controller/AdminController.java
package com.hostel.hostelmanagementsystem.controller.admin;

import com.hostel.hostelmanagementsystem.dto.AdminLoginDTO;
import com.hostel.hostelmanagementsystem.dto.AdminLoginResponseDTO;
import com.hostel.hostelmanagementsystem.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Handles POST requests to authenticate an admin.
     * <p>
     * Request Format:
     * POST /api/admin/login
     * <p>
     * Purpose:
     * This method verifies admin login credentials and returns authentication results.
     * <p>
     * Flow:
     * - Accepts login data (username and password) via the request body as an AdminLoginDTO.
     * - Calls AdminService to perform authentication.
     * - If the username is not found, returns HTTP 401 with an appropriate message.
     * - If the password is incorrect, returns HTTP 401 with an appropriate message.
     * - If authentication succeeds, returns HTTP 200 with success message and admin ID.
     *
     * @param loginDTO the admin's login credentials
     * @return ResponseEntity with success or failure message and admin ID if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminLoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();

        AdminLoginResponseDTO result = adminService.login(loginDTO);

        if (result.getMessage().equals("Username not found")) {
            response.put("success", false);
            response.put("message", "Username not found");
            return ResponseEntity.status(401).body(response);
        }

        if (result.getMessage().equals("Invalid password")) {
            response.put("success", false);
            response.put("message", "Invalid password");
            return ResponseEntity.status(401).body(response);
        }

        response.put("success", true);
        response.put("message", result.getMessage());
        response.put("adminId", result.getAdminId());
        return ResponseEntity.ok(response);
    }
}