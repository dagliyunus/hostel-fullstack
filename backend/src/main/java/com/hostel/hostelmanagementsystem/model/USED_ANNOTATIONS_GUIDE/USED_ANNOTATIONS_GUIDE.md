This document provides an overview of annotations used within the `model` package of the HostelManagementSystem project. It explains the purpose of each annotation and provides relevant usage examples from the codebase.

##  JPA Annotations
         
These are used to map Java objects (entities) to database tables.

### `@Entity`
This annotation is used above model classes such as `Booking`, `Customer`, and `Room`. It simply tells JPA: “Hey, this class represents a table in the database.”


@Entity
public class Customer { }


### `@Table`
This is an optional annotation used to explicitly define the table name or schema:


@Table(name = "customers")


### `@Id`
Marks the field as the primary key. This annotation is used in nearly every model:


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


### `@GeneratedValue`
Tells JPA how to generate the `@Id`. Most of the time, `IDENTITY` is used so the database handles auto-incrementing.

### `@Column`
Used to customize the DB column. This annotation is applied for fields like `firstName`, where there is a need to limit length or enforce non-null values:


@Column(nullable = false, length = 100)
private String firstName;


### `@ManyToOne`
Used in relationships like a `Booking` referencing a `Room` or `Customer`.


@ManyToOne
@JoinColumn(name = "room_id")
private Room room;


### `@JoinColumn`
Defines the foreign key column name for relational mapping.

### `@Enumerated`
This is appropriate for mapping enums such as `BookingStatus`:


@Enumerated(EnumType.STRING)
@Column(name = "booking_status")
private BookingStatus bookingStatus;


### `@Transient`
Sometimes, it is necessary to include a field for convenience (like `roomNumber`) but not have it persisted in the DB:


@Transient
private String roomNumber;


---

##  Lombok Annotations

These reduce boilerplate code like getters, setters, constructors, etc.

### `@Getter` / `@Setter`
These annotations are used everywhere! Lombok will generate the getter/setter methods automatically.

### `@Data`
This annotation is useful for adding a bunch of boilerplate at once: it includes getters, setters, `equals()`, `hashCode()`, `toString()`...

@Data
public class Room {  }

### `@NoArgsConstructor` / `@AllArgsConstructor`
These annotations are used when both an empty constructor and a full constructor are needed for a class like `Notification`.

---

##  Jackson Annotation

### `@JsonManagedReference`
This annotation is used to avoid infinite loops when serializing relationships to JSON, especially in one-to-many and many-to-one mappings.

