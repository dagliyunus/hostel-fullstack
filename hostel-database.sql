CREATE DATABASE HostelManagementSystem;
USE HostelManagementSystem;

-- 1. Admin Table
CREATE TABLE Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- 2. Customer Table
CREATE TABLE Customer (
    customer_id VARCHAR(50) NOT NULL PRIMARY KEY,
    room_id VARCHAR(50),
    bed_id VARCHAR(50),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    phone VARCHAR(20),
    registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES Room(room_id) ON DELETE SET NULL
);

-- 3. Room Table
CREATE TABLE Room (
    room_id VARCHAR(50) PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    floor INT,
    capacity INT NOT NULL  -- e.g., 4 for 4-bed ,Admin can update capacity = 3 from dashboard.
);

-- 4. Bed Table
CREATE TABLE Bed (
    bed_id VARCHAR(50) PRIMARY KEY,
    bed_number VARCHAR(10) UNIQUE NOT NULL,
    room_id VARCHAR(50),
    FOREIGN KEY (room_id) REFERENCES Room(room_id) ON DELETE CASCADE
);

-- 5. Booking Table
CREATE TABLE Booking (
    booking_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(50),
    customer_id VARCHAR(50),
    bed_id VARCHAR(50),
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status ENUM('Booked', 'Cancelled', 'Completed') NOT NULL DEFAULT 'Booked',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (bed_id) REFERENCES Bed(bed_id) ON DELETE CASCADE,
    CONSTRAINT no_double_booking UNIQUE (bed_id, check_in_date, check_out_date)
);

CREATE VIEW BedAvailability AS
SELECT
    b.bed_id,
    b.bed_number,
    b.room_id,
    r.room_number,
    CASE
        WHEN EXISTS (
            SELECT 1 FROM Booking bk
            WHERE bk.bed_id = b.bed_id
              AND CURRENT_DATE < bk.check_out_date
              AND CURRENT_DATE >= bk.check_in_date
              AND bk.status = 'Booked'
        )
        THEN 'Occupied'
        ELSE 'Available'
    END AS availability
FROM Bed b
JOIN Room r ON b.room_id = r.room_id;

-- 6. Payment Table
CREATE TABLE Payment (
    payment_id VARCHAR(50) PRIMARY KEY,
    booking_id INT,
    payment_type ENUM('Credit Card', 'Cash', 'Paypal') NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE
);

CREATE TABLE Notification (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_full_name VARCHAR(255),
    booking_id INT,
    check_in_date DATE,
    check_out_date DATE,
    room_number VARCHAR(50),
    bed_number VARCHAR(50),
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    total_price DECIMAL(10,2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contact_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_booking_dates ON Booking (bed_id, check_in_date, check_out_date);
CREATE INDEX idx_booking_customer ON Booking (customer_id);
CREATE INDEX idx_booking_bed ON Booking (bed_id);
CREATE INDEX idx_payment_booking ON Payment (booking_id);
CREATE INDEX idx_room_number ON Room (room_number);
CREATE INDEX idx_customer_email ON Customer (email);
CREATE INDEX idx_notification_booking_id ON Notification (booking_id);
CREATE INDEX idx_notification_is_read ON Notification (is_read);
CREATE INDEX idx_notification_created_at ON Notification (created_at);


