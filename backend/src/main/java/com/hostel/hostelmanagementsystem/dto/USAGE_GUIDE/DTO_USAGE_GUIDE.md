# DTO Usage Guide for Hostel Management System

## What is a DTO?
DTO (Data Transfer Object) is a design pattern used to transfer data between layers (usually Controller to Frontend) while avoiding unnecessary entity exposure.

## Why do I use DTOs in this project?
- To limit the data sent to frontend (In this way I may avoid exposing sensitive fields).
- To combine fields from related entities (e.g., `roomNumber` from Room in `AdminCustomerDTO`).
- To prevent serialization issues (like lazy loading of relationships).
- To simplify frontend integration with a clean and structured response format.

## Conventions
- All DTOs are placed under `com.hostel.hostelmanagementsystem.dto`.
- DTOs are typically used in Service → Controller layer transition.

## Notes
- In this project, DTOs are NOT mapped automatically (no MapStruct used) to keep the mapping logic transparent and easy to control manually. 
- This avoids adding an extra dependency and makes debugging and custom transformation easier.
- Manual conversion is done via `.stream().map(c -> new DTO(...))` logic.