# Restaurant Management System with Java Threads and Semaphores

## Introduction
This project aims to simulate the daily operations of a restaurant using multithreading concepts in Java. The system manages customer groups, waiters, chefs, and a cashier, each handled as separate threads. The goal is to implement thread synchronization and resource management in a real-time environment.


## Project Screen

![WhatsApp Image 2024-03-08 at 12 11 32](https://github.com/silasener/Restaurant-Management-System/assets/105547660/31daf257-1c7a-49a1-a37b-534101b7be34)

## Project Overview
The restaurant management system consists of the following components:





1. **Processes:**
   - Customers entering the restaurant
   - Waiters taking orders
   - Chefs preparing orders
   - Customers making payments

2. **Threads:**
   - Each customer is represented as a separate thread upon entering the restaurant.
   - Each waiter works in a separate thread to serve customers.
   - Each chef works in a separate thread to manage the cooking process.
   - Cashier operations, payment collection, and table clearing are handled in a separate thread.

3. **Synchronization:**
   - Synchronization is required among customer threads for table selection and seating order.
   - Waiter threads coordinate order taking and serve their assigned customers.
   - Chefs coordinate with limited cooking resources to avoid conflicts.
   - The cashier thread handles payment processing for one order at a time.

## Technologies Used
- Java threads
- Semaphores for synchronization
- Multi-threading concepts for parallel processing

## Usage
1. **Installation:**
   - Clone the repository:
     ```
     git clone https://github.com/your/repository.git
     ```
   - Navigate to the project directory:
     ```
     cd project-directory
     ```
   - Install any necessary dependencies.

2. **Running the Application:**
   - Start the application using your preferred Java IDE or command line tools.

3. **Accessing the Interface:**
   - The application should provide a user-friendly graphical interface to interact with the restaurant simulation.

## Important Notes
- All actions performed by the system should be logged in real-time to a text file.
- Presentation of static values (such as time intervals and quantities) may be subject to dynamic updates during runtime.
