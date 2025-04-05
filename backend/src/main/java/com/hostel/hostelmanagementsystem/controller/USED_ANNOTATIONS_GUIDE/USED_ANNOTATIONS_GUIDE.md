This guide documents the most frequently used annotations in the controller layer of the HostelManagementSystem. These annotations help us define RESTful endpoints, manage dependency injection, and handle cross-origin requests—enabling a clear and scalable API architecture.

⸻

✅ @CrossOrigin

Purpose:
Allows cross-origin requests from specified domains. In my case, I use:

@CrossOrigin(origins = "http://localhost:5173")

Why:
The frontend (likely built with React or Vue) runs on a different port than the Spring Boot backend. Without this annotation, browsers would block the frontend from calling backend APIs due to CORS policy.

⸻

✅ @RestController

Purpose:
Marks the class as a RESTful controller where each method returns a domain object (typically JSON), rather than rendering a view.

Why:
This is ideal for an API-based system like mine, especially since the frontend is decoupled. It internally combines @Controller and @ResponseBody.

⸻

✅ @RequestMapping

Purpose:
Sets the base URL for all endpoints inside the controller.

Why:
I use it to group related endpoints under a common route, like:

@RequestMapping("/api/admin/dashboard/manageRoom")

his makes endpoint structures predictable and manageable, especially as your API grows.

⸻

✅ @Autowired

Purpose:
Injects dependencies into Spring-managed beans.

Why:
Used in my controller constructors to inject service layer beans like RoomService, ensuring that controller logic stays clean and business logic is delegated properly:

@Autowired
public AdminRoomController(RoomService roomService) {
this.roomService = roomService;
}


⸻

✅ @GetMapping

Purpose:
Handles HTTP GET requests.

Why:
Used for “read” operations like:
•	Fetching all rooms
•	Getting room details
•	Retrieving rooms with beds

Example:

@GetMapping("/getAllRooms")

✅ @PostMapping

Purpose:
Handles HTTP POST requests.

Why:
Used for creating resources like rooms, beds, or users. This is also how I submit forms or DTO payloads from the frontend.

Example:

@PostMapping("/createRoomWithBeds")

✅ @DeleteMapping

Purpose:
Handles HTTP DELETE requests.

Why:
Used for removing resources. In my case:

@DeleteMapping("/deleteRoom")

It’s used in admin flows for deleting rooms from the system.

⸻

✅ @PutMapping

Purpose:
Handles HTTP PUT requests.

Why:
Used to update existing records. The admin panel uses it for operations like:

@PutMapping("/updateRoom")

Which modifies existing room records based on updated values provided by the admin.


⸻

✅ @Component

Purpose:
Marks a class as a Spring-managed component.
Spring detects this annotation during component scanning and registers it as a bean in the application context.

Typical use in project:
Used in utility or stateful classes like caching handlers:

@Component
public class BookingCache { ... }


⸻

✅ @RequestBody

Purpose:
Maps the HTTP request body to a Java object, usually a DTO.

Example:

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AdminLoginDTO loginDTO) { ... }

Used in POST and PUT requests to transfer complex JSON payloads into Java.

⸻

✅ @RequestParam

Purpose:
Used to extract query parameters from the URL.

Example:

@GetMapping("/byBooking")
public ResponseEntity<?> getPaymentByBookingId(@RequestParam("bookingId") Integer bookingId) { ... }

