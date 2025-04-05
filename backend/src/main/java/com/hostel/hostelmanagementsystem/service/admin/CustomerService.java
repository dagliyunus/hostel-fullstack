package com.hostel.hostelmanagementsystem.service.admin;

import com.hostel.hostelmanagementsystem.dto.AdminCustomerDTO;
import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Customer;
import com.hostel.hostelmanagementsystem.model.Room;
import com.hostel.hostelmanagementsystem.repository.BedRepository;
import com.hostel.hostelmanagementsystem.repository.CustomerRepository;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    /**
     * Constructor-based dependency injection of CustomerRepository.
     * Spring automatically injects an instance of the repository when the service is created.
     *
     * @param customerRepository the repository used to access customer data
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository, RoomRepository roomRepository, BedRepository bedRepository) {
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    public Optional<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Retrieves a customer by their email address.
     * This method is used when searching for a specific customer,
     * and admin searches.
     * <p>
     * The return type is Optional to safely handle the case where
     * no customer with the given email is found, avoiding null pointer exceptions.
     *
     * @param email the email address of the customer to retrieve
     * @return an Optional containing the Customer if found, or empty if not found
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * Retrieves all customers from the database and maps them into AdminCustomerDTO objects.
     * This method is specifically used to populate the customer list view in the admin dashboard.
     * <p>
     * It uses Java Stream API to transform a List of Customer entities into a List of DTOs.
     * The .stream() call converts the list into a stream for processing,
     * .map(c -> ...) transforms each Customer object into an AdminCustomerDTO using a constructor,
     * and .collect(Collectors.toList()) gathers the results back into a List.
     * <p>
     * Each customer entity is transformed to a DTO containing key information such as:
     * ID, name, email, phone, date of birth, registration date, and associated room number.
     *
     * @return a list of AdminCustomerDTOs representing all registered customers
     */
    public List<AdminCustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(c -> new AdminCustomerDTO(
                        c.getCustomerId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getPhone(),
                        c.getDateOfBirth(),
                        c.getRoom() != null ? c.getRoom().getRoomNumber() : null,
                        c.getBed() != null ? c.getBed().getBedNumber() : null,
                        c.getRegisteredAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Generates a new unique customer ID with a "C" prefix.
     * <p>
     * Purpose:
     * This method retrieves all existing customers, extracts the numeric portion of their IDs,
     * finds the maximum numeric value, and returns a new ID by incrementing this value.
     * <p>
     * Flow:
     * - Fetches all customers from the repository.
     * - Filters and parses IDs that start with "C" and are numeric.
     * - Determines the highest number and increments it to form the next ID.
     * <p>
     * Example:
     * If existing IDs are [C1, C2, C5], the method returns "C6".
     *
     * @return the next available customer ID in the format "C{number}"
     */
    public String generateNextCustomerId() {
        List<Customer> allCustomers = customerRepository.findAll();

        int max = allCustomers.stream()
                .map(Customer::getCustomerId)
                .filter(id -> id.startsWith("C"))
                .map(id -> id.substring(1))     // remove "C"
                .filter(s -> s.matches("\\d+")) // digits only
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "C" + (max + 1);
    }

    /**
     * Creates a new customer record in the system.
     * <p>
     * Purpose:
     * This method is used to register a new customer and associate them with an available bed in a specified room.
     * <p>
     * Flow:
     * - Validates that a room number is provided and exists.
     * - Finds the first available (unoccupied) bed in the room.
     * - Maps the provided DTO to a Customer entity.
     * - Assigns room, bed, and a newly generated customer ID.
     * - Saves the new customer and returns the saved data as a DTO.
     * <p>
     * Error Handling:
     * - Throws IllegalArgumentException if room number is missing or not found.
     * - Throws IllegalStateException if no available beds are found in the room.
     *
     * @param dto the AdminCustomerDTO containing customer data from the client
     * @return AdminCustomerDTO representation of the saved customer
     */
    @Transactional
    public AdminCustomerDTO createCustomer(AdminCustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setRegisteredAt(dto.getRegisteredAt());

        if (dto.getRoomNumber() != null) {
            Room room = roomRepository.findByRoomNumber(dto.getRoomNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Room number not found: " + dto.getRoomNumber()));
            customer.setRoom(room);

            List<Bed> bedsInRoom = bedRepository.findByRoom(room);
            for (Bed bed : bedsInRoom) {
                boolean isOccupied = customerRepository.existsByBed(bed);
                if (!isOccupied) {
                    customer.setBed(bed);
                    break;
                }
            }

            if (customer.getBed() == null) {
                throw new IllegalStateException("No available beds in room: " + room.getRoomNumber());
            }
        } else {
            throw new IllegalArgumentException("Room number must be provided.");
        }

        customer.setCustomerId(generateNextCustomerId());

        Customer saved = customerRepository.save(customer);
        return mapToDTO(saved);
    }

    /**
     * Updates an existing customer's information in the system.
     * <p>
     * Purpose:
     * This method allows administrators to update customer details such as name, contact info,
     * date of birth, registration date, and their assigned room and bed.
     * <p>
     * Flow:
     * - Retrieves the current customer by ID.
     * - Updates the customer’s personal and contact fields.
     * - If a room number is provided, it fetches the room and assigns the first available bed.
     * - Throws exceptions if the customer is not found, the room doesn't exist, or there are no beds available.
     *
     * @param dto the AdminCustomerDTO containing the updated customer information
     * @return the updated Customer entity after persistence
     * @throws IllegalArgumentException if the customer or room cannot be found
     * @throws IllegalStateException if no available beds exist in the specified room
     */
    @Transactional
    public Customer updateCustomer(AdminCustomerDTO dto) {
        Optional<Customer> currentCustomerOpt = customerRepository.findById(dto.getCustomerId());

        if (currentCustomerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with id " + dto.getCustomerId() + " not found");
        }

        Customer currentCustomer = currentCustomerOpt.get();

        //  Update personal fields
        currentCustomer.setFirstName(dto.getFirstName());
        currentCustomer.setLastName(dto.getLastName());
        currentCustomer.setEmail(dto.getEmail());
        currentCustomer.setPhone(dto.getPhone());
        currentCustomer.setDateOfBirth(dto.getDateOfBirth());
        currentCustomer.setRegisteredAt(dto.getRegisteredAt());

        //  Auto-fetch Room by roomNumber
        if (dto.getRoomNumber() != null) {
            Room room = roomRepository.findByRoomNumber(dto.getRoomNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Room number not found: " + dto.getRoomNumber()));
            currentCustomer.setRoom(room);

            //  Auto-assign first available Bed in Room
            List<Bed> bedsInRoom = bedRepository.findByRoom(room);
            for (Bed bed : bedsInRoom) {
                boolean isOccupied = customerRepository.existsByBed(bed);
                if (!isOccupied) {
                    currentCustomer.setBed(bed);
                    break;
                }
            }

            if (currentCustomer.getBed() == null) {
                throw new IllegalStateException("No available beds in room: " + room.getRoomNumber());
            }
        }

        return customerRepository.save(currentCustomer);
    }

    /**
     * Deletes a customer record from the system based on the provided customer ID.
     * <p>
     * Purpose:
     * This method allows administrators to remove a customer from the database.
     * It is used when a customer is no longer associated with the hostel or data cleanup is required.
     * <p>
     * Flow:
     * - Searches for the customer by their ID using the repository.
     * - If the customer is not found, throws an IllegalArgumentException.
     * - If found, deletes the customer entity from the database.
     *
     * @param customerId the unique identifier of the customer to be deleted
     * @throws IllegalArgumentException if the customer is not found
     */
    @Transactional
    public void deleteCustomer(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
        }

        customerRepository.delete(customerOpt.get());
    }

    /**
     * Converts a Customer entity into an AdminCustomerDTO.
     * <p>
     * Purpose:
     * Used to convert the internal Customer model into a format suitable for
     * administrative frontend use, exposing only relevant fields.
     * <p>
     * Flow:
     * - Extracts and maps fields such as ID, name, contact information, room and bed assignments.
     * - Safely handles null checks for room and bed to prevent NullPointerExceptions.
     *
     * @param customer the Customer entity to convert
     * @return an AdminCustomerDTO populated with the customer’s data
     */
    public AdminCustomerDTO mapToDTO(Customer customer) {
        return new AdminCustomerDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getDateOfBirth(),
                customer.getRoom() != null ? customer.getRoom().getRoomNumber() : null,
                customer.getBed() != null ? customer.getBed().getBedNumber() : null,
                customer.getRegisteredAt()
        );
    }
}
