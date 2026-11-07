<div align="center">

<img src="src/main/resources/static/images/Logo_large.png" alt="ComplexLab Logo" width="600" />

# ComplexLab

### Complex Number & Matrix Mathematics Platform

**An interactive mathematical workspace for complex arithmetic, advanced matrix operations, visual exploration, and calculation history.**

Java 21 · Spring Boot 4.1.2 · Thymeleaf · JavaScript · SQLite · Maven

[Overview](#overview) · [Core Features](#core-features) · [Screenshots](#application-screenshots) · [Installation](#installation--usage) · [Architecture](#application-architecture)

</div>

---

## Overview

**ComplexLab** is a full-stack web application for working with **complex numbers** and **matrices of complex numbers**. It brings together a Java mathematical engine, Spring Boot MVC, a responsive Thymeleaf interface, interactive browser-based visualizers, and a local SQLite calculation history.

The project builds on a university assignment first developed in **2019**, expanding the original mathematical functionality into a modern web application for experimentation and visual learning.

## Core Features

| Module | Capabilities |
| --- | --- |
| **Complex Number Calculator** | Addition, subtraction, multiplication, and division of complex numbers, with input validation and formatted results. |
| **Complex Matrix Calculator** | Matrix addition, subtraction, multiplication, complex-scalar multiplication, transpose, and conjugate transpose. |
| **Advanced Matrix Operations** | Determinant, trace, inverse (where defined), powers, identity matrices, and random matrix generation. |
| **Complex Number Visualizer** | Interactive Argand plane with draggable vector endpoints, adjustable scale, magnitude, and argument. |
| **Complex Matrix Visualizer** | Heatmap representation of complex matrix entries, with real/imaginary, magnitude, and argument views and selected-cell inspection. |
| **Calculation History** | SQLite-backed storage for successful computations, filtering by calculation type, and record deletion. |
| **Responsive Interface** | Dashboard, navigation sidebar, shared Thymeleaf layout fragments, and custom PNG iconography. |

### Matrix Input and Validation

Matrices entered in the calculator support dimensions up to **10 × 10**. Each matrix row occupies one line, with values separated by spaces. Complex values follow the `real:imaginary` format; for example, `3:-2` represents `3 − 2i`.

Operations are validated against mathematical requirements, including compatible matrix dimensions and invertibility where applicable.

## Application Screenshots

### Dashboard

The main navigation dashboard provides access to the mathematical tools, visualizers, and calculation history.

![ComplexLab Dashboard](docs/screenshots/dashboard.png)

### Complex Number Operations

Calculate arithmetic expressions involving complex numbers.

![Complex Number Operations](docs/screenshots/complex-number-operations.png)

### Complex Matrix Operations

Perform standard and advanced operations on matrices with complex entries.

![Complex Matrix Operations](docs/screenshots/complex-matrix-operations.png)

### Complex Number Visualizer

Explore the geometric interpretation of complex numbers on an interactive complex plane.

![Complex Number Visualizer](docs/screenshots/complex-number-visualizer.png)

### Complex Matrix Visualizer

Examine matrix entries through graphical representations and interactive inspection.

![Complex Matrix Visualizer](docs/screenshots/complex-matrix-visualizer.png)

### Calculation History

Review and manage previously saved calculations.

![Calculation History](docs/screenshots/history.png)

---

## Technology Stack

| Layer | Technology |
| --- | --- |
| **Programming Language** | Java 21 |
| **Backend** | Spring Boot 4.1.2, Spring MVC |
| **Frontend** | Thymeleaf, HTML, CSS, JavaScript |
| **Interactive Visualization** | Vanilla JavaScript, HTML Canvas |
| **Persistence** | SQLite, Spring Data JPA, Hibernate ORM, SQLite JDBC |
| **Build & Dependencies** | Maven, Maven Wrapper |
| **Testing** | JUnit, Spring Boot Test |

## Application Architecture

The project separates mathematical logic, request processing, persistence, and presentation into dedicated layers:

- **Controllers** handle web routes, user input, and view rendering.
- **DTOs and enums** represent requests, responses, and available mathematical operations.
- **Models and services** implement complex arithmetic, matrix calculations, and calculation-history logic.
- **Repositories** persist history entries to SQLite.
- **Thymeleaf templates and JavaScript** provide the responsive interface and visual interactions.

### Application Pages

| Page | Route |
| --- | --- |
| Dashboard | `/` |
| Complex Number Calculator | `/calculator` |
| Complex Matrix Calculator | `/matrix` |
| Complex Number Visualizer | `/visualizer` |
| Complex Matrix Visualizer | `/matrix-visualizer` |
| Calculation History | `/history` |

## Installation & Usage

### Requirements

- **JDK 21** or newer (the project targets Java 21).
- A browser.
- Maven, or the Maven Wrapper included in the repository.

### 1. Clone the Repository

```bash
git clone https://github.com/KonKargas/ComplexLab.git
cd ComplexLab
```

### 2. Prepare Local Storage

The repository contains a `data/` directory. Make sure this directory exists and is writable. The SQLite database file, `data/complexlab.db`, is created on first startup and is not tracked in Git.

### 3. Run the Application

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
./mvnw spring-boot:run
```

Alternatively, with an installed Maven distribution:

```bash
mvn spring-boot:run
```

Open **http://localhost:8080/** in your browser.

> **Database configuration:** The SQLite database path is relative to the working directory. Run the application from the repository root, or configure `COMPLEXLAB_DB_URL` to override the connection string (e.g. `jdbc:sqlite:/absolute/path/complexlab.db`).

### 4. Run Tests

```bash
./mvnw test
```

On Windows, use `.\mvnw.cmd test`.

## Project Structure

```text
ComplexLab/
├── data/                                # Local SQLite storage directory
├── docs/
│   └── screenshots/                     # Application screenshots
├── src/
│   ├── main/
│   │   ├── java/com/kkargas/complexlab/
│   │   │   ├── controller/             # MVC endpoints
│   │   │   ├── dto/                    # Input/output models
│   │   │   ├── enums/                  # Supported operations
│   │   │   ├── model/                  # Math and persistence models
│   │   │   ├── repository/             # Data access
│   │   │   └── service/                # Business and math logic
│   │   └── resources/
│   │       ├── static/                 # CSS, JS, application logo and icons
│   │       ├── templates/              # Views and layout fragments
│   │       └── application.properties
│   └── test/                            # Automated tests
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Data & Privacy

Calculation history is saved **locally** in `data/complexlab.db`. The database, its journals, generated build artifacts, and IDE-specific files are excluded from the public repository.

The current application uses `spring.jpa.hibernate.ddl-auto=update` to simplify local development. Before changing schemas or deleting data, back up your local database. A production deployment would benefit from controlled schema migrations.

## Project Background

ComplexLab began as an academic programming assignment in **2019**. The current version retains its mathematical focus while extending it with a web interface, richer operations, graphical tools, and persistent history. It demonstrates full-stack Java development alongside reusable mathematical modeling.

## Repository Scope & Licensing

This repository provides the application's source code, Maven build files, and documentation. Local calculation-history data is not included.

**No license has been selected for this repository.** Unless a license is added, the code should not be assumed to be licensed for redistribution or reuse.

---

## Author

**Konstantinos Kargas**  
Full-Stack Software Engineer

- Website: [kkargas.com](https://kkargas.com)
- GitHub: [KonKargas](https://github.com/KonKargas)

---

*ComplexLab — advanced complex mathematics, interactive visualization, and organized calculation history.*
