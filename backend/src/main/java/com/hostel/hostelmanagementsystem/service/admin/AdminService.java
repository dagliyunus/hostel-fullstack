package com.hostel.hostelmanagementsystem.service.admin;

import com.hostel.hostelmanagementsystem.dto.AdminLoginDTO;
import com.hostel.hostelmanagementsystem.dto.AdminLoginResponseDTO;
import com.hostel.hostelmanagementsystem.model.Admin;
import com.hostel.hostelmanagementsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {


    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Authenticates an admin user based on provided login credentials.
     * <p>
     * This method retrieves the admin by username and verifies the password using BCrypt.
     * If authentication is successful, a success response is returned with a token and admin ID.
     * Otherwise, a corresponding error message is returned.
     * <p>
     * Note: The token returned in this implementation is a placeholder ("fake-token") and should be
     * replaced with a real token mechanism (e.g., JWT) in a production system.
     *
     * @param loginDTO the login data containing username and password
     * @return AdminLoginResponseDTO containing a status message, optional token, and admin ID
     */
    public AdminLoginResponseDTO login(AdminLoginDTO loginDTO) {
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(loginDTO.getUsername());

        if (optionalAdmin.isEmpty()) {
            return new AdminLoginResponseDTO("Username not found", null, null);
        }

        Admin admin = optionalAdmin.get();

        if (!BCrypt.verifyer().verify(loginDTO.getPassword().toCharArray(), admin.getPasswordHash()).verified) {
            return new AdminLoginResponseDTO("Invalid password", null, null);
        }

        return new AdminLoginResponseDTO("Login successful", "fake-token", admin.getAdminId());
    }
}