# E-Commerce Clothing Website

This project is a **full-stack e-commerce application** built with **Java Spring Boot (backend)** and **React (frontend)**. The application allows users to browse, search, and purchase clothing items online. It includes features for customers, administrators, and secure order management.

## Features

### User & Authentication

* User registration and login (hashed password storage)
* Role management (Customer / Admin)
* Address management for shipping

### Product Catalog

* Add, update, delete, and list clothing products
* Product categories, sizes, and colors
* Image support for products

### Shopping & Cart

* Add products to cart
* Update or remove items in cart
* Automatic cart assignment per user

### Orders & Checkout

* Place orders from cart
* Order summary with total price
* Track order status (Pending, Paid, Shipped, Delivered, Canceled)

### Payments

* Multiple payment methods (Card, EFT, Cash on Delivery)
* Payment status tracking

### Shipment & Delivery

* Shipment tracking with carrier and tracking number
* Delivery status updates

### Discounts (Optional)

* Coupons and promotions support
* Apply discount codes during checkout

### Admin Features

* Manage products, stock, and categories
* View customer orders
* Update shipping and delivery status
* Audit logs for tracking admin actions

## Tech Stack

* **Backend:** Java, Spring Boot, Spring Data JPA, Hibernate
* **Frontend:** React, HTML5, CSS3, JavaScript
* **Database:** MySQL
* **Security:** Spring Security, JWT Authentication
* **Build Tool:** Maven / Gradle

## Project Structure

* `domain/` – Entity classes (User, Product, Order, etc.)
* `repository/` – JPA repositories for database access
* `service/` – Business logic for e-commerce features
* `controller/` – REST APIs for frontend communication
* `frontend/` – React application for UI

## Future Enhancements

* Wishlist and favorites
* Product reviews and ratings
* Email notifications for orders and shipments
* Analytics dashboard for admins

---
