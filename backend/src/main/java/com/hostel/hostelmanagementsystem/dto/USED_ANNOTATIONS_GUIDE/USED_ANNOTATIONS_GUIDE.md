This guide documents the commonly used annotations within the dto (Data Transfer Object) directory of the HostelManagementSystem project.

In this layer, DTOs are used to carry data between different layers of the application without exposing entities directly. To reduce boilerplate code (like getters, setters, constructors, etc.), Lombok annotations are heavily utilized.

⸻

✅ @Getter

Purpose:
Automatically generates getter methods for all fields in the class.

Why it matters:
Instead of manually writing:

public String getTitle() {
    return title;
}

…I just annotate the class with @Getter and it’s handled at compile time.


⸻

✅ @Setter

Purpose:
Automatically generates setter methods for all fields.

Example:

public void setTitle(String title) {
    this.title = title;
}

Combined with @Getter to make the DTO fully mutable.


⸻

✅ @Data

Purpose:
A convenient shortcut annotation that combines:
	•	@Getter
	•	@Setter
	•	@RequiredArgsConstructor
	•	@ToString
	•	@EqualsAndHashCode

I use @Data when I want an all-in-one solution for common boilerplate. If I only want getters and setters , I can just use @Getter and @Setter.

Example in project:

@Data
public class ContactDTO {
    private String name;
    private String email;
    private String message;
    private boolean isRead = false;
}