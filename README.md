# Developer & Project Management System

A Java-based console application for managing software developers and their projects.
The system allows users to maintain developer profiles, assign projects, and track developer experience.

## 🚀 Features

* Manage developer profiles

  * Add new developers
  * Search developers by ID
  * Update developer salary
  * Delete developers
  * List developers by programming language

* Project management

  * Assign projects to developers
  * View projects grouped by developer
  * Calculate total developer experience (in months)
  * Display projects ending in a specific year

* Data persistence

  * Load developer and project data from files
  * Save updated data back to files

* Utility features

  * Input validation
  * Menu-driven command-line interface
  * Sorting developers by salary

## 🛠️ Technologies Used

* Java
* Object-Oriented Programming (OOP)
* File Handling
* Regular Expressions
* CLI (Command Line Interface)

## 📂 Project Structure

```
src
 ├── business
 │    ├── DeveloperManager.java
 │    ├── ProjectManager.java
 │    └── ManagementSystem.java
 │
 ├── model
 │    ├── Developer.java
 │    └── Project.java
 │
 ├── tools
 │    ├── Inputter.java
 │    ├── Menu.java
 │    ├── FileUtils.java
 │    └── Acceptable.java
 │
 └── main
      └── Main.java
```

## 📊 System Capabilities

* Developer validation with regex format `DEVxxx`
* Salary validation
* Project duration tracking
* Start date validation
* Data stored in text files

## ▶️ How to Run

1. Clone the repository

```
git clone https://github.com/your-username/developer-project-management-system.git
```

2. Open the project in **IntelliJ IDEA / NetBeans / VS Code**

3. Run:

```
main/Main.java
```

4. Use the console menu to interact with the system.

## 📚 Learning Objectives

This project demonstrates:

* Object-Oriented Programming design
* Data validation
* File-based persistence
* Separation of concerns (Model – Business – Tools)
* Menu-driven CLI applications

## 👨‍💻 Author

Student project for practicing **Java OOP and basic system design**.
