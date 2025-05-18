# Halal Cash: Interest-Free Mobile Finance Application

## 🚀 Project Overview

Halal Cash is an innovative Android e-wallet and mobile finance application designed to provide users with a Sharia-compliant financial management solution. Its core purpose is to enable financial transactions and management *without interest*, addressing a significant need for individuals who adhere to Islamic finance principles, where interest (Riba) is prohibited. While primarily catering to this demographic, the application is also built to be flexible and inclusive, allowing any ideologically different individuals to use the system and optionally pause interest-based interactions.

This project was developed from a client's concept, which was subsequently refined to create a self-sufficient and scalable financial ecosystem.

## ✨ Key Features

* **Interest-Free Transactions:** Core functionality built around Sharia-compliant financial principles.
* **Personal Finance Management:** Tools for tracking income, expenses, and managing budgets.
* **Customizable Interaction:** Option for users to disable or manage interest-based interactions according to their beliefs.
* **User-Friendly Interface:** Intuitive and clean design for seamless navigation and experience.
* **Locked Transactions:** Feature to prevent unwanted or fraudulent transactions, ensuring financial security.
* **Interest-Free Loans:** Provides a mechanism for granting loans without any interest, aligning with ethical financial practices.
* **Multi-Asset Management System:** Ability to manage multiple asset types, including various forms of currency and commodities like `Gold`, `Silver`, `Platinum`, and `BDT` (Bangladeshi Taka).
* **Decentralized Banking Concept(Not implemented):** Designed conceptually to operate without the direct requirement of traditional bank accounts for certain functionalities.

## 🛠️ Technology Stack

This project leverages a modern and robust technology stack for Android development, utilizing various libraries and frameworks to enhance functionality and user experience:

**Core Android Development:**
* **Languages:** `Java`, `Kotlin`
* **IDE:** `Android Studio`
* **UI/UX Design:** `XML`, `ViewBinding`
* **Minimum SDK:** `API 24`
* **Target SDK:** `API 33`
* **AppCompat:** `androidx.appcompat:appcompat:1.6.1` - For backward compatibility of newer UI features.
* **Material Design:** `com.google.android.material:material:1.11.0` - Implementing Material Design principles for a modern UI.
* **ConstraintLayout:** `androidx.constraintlayout:constraintlayout:2.1.4` - For flexible and efficient UI layouts.
* **SwipeRefreshLayout:** `androidx.swiperefreshlayout:swiperefreshlayout:1.1.0` - For implementing pull-to-refresh functionality.

**Networking (API Communication):**
* **Retrofit:** `com.squareup.retrofit2:retrofit:2.9.0` - A type-safe HTTP client for Android and Java.
* **Gson Converter:** `com.squareup.retrofit2:converter-gson:2.5.0` - A Retrofit converter factory for JSON serialization and deserialization using Gson.

**Image Loading:**
* **Glide:** `com.github.bumptech.glide:glide:4.12.0` - A fast and efficient image loading library for Android.

**Activity Component:**
* **AndroidX Activity:** `androidx.activity:activity:1.9.0` - For base activity components.

**Internal Library:**
* **mbViewLib:** `project(":app:mbViewLib")` - Indicates a local module or library within the project.

**Testing:**
* **JUnit:** `junit:junit:4.13.2` - Standard testing framework for Java.
* **AndroidX Test JUnit Extension:** `androidx.test.ext:junit:1.1.5` - JUnit extension for Android testing.
* **Espresso:** `androidx.test.espresso:espresso-core:3.5.1` - UI testing framework for Android.

**Backend & API:**
* **Framework:** `Laravel` (PHP)
* **Database:** (Implicitly SQL-based, likely `MySQL` or `PostgreSQL` given Laravel)
* **API Type:** `REST APIs` with `Token-Based Authentication`
* **Hosting:** `Namecheap Web Hosting`

**Design & Collaboration Tools:**
* **UI/UX Design:** `Adobe XD` (initial), `Figma` (later)
* **Version Control:** `Git`, `GitHub`
* **Project Management:** `Trello`

## ⚙️ Architecture & Development Process

The development followed a structured Software Development Life Cycle (SDLC), moving from theoretical stages to practical implementation.

* **Theoretical Stage:** Involved `SDLC methodologies`, `Continuous Integration` concepts, comprehensive `System Design`, and `Entity-Relationship (ER) Diagrams` for database planning.
* **Development Stage:** Focused on Android application deployment, with a clear separation between front-end (mobile app) and back-end (API) components communicating via secured REST APIs.

## 👥 My Role & Contributions

As the primary **Front-End Mobile App Developer** for Halal Cash, my responsibilities included:

* Developing the Android application using Java, Kotlin, XML, and SQLite.
* Collaborating with the UI/UX designer to translate designs into functional mobile interfaces.
* Integrating with the Laravel-based backend via REST APIs.

Beyond development, I also played a significant role in:

* **Requirements Clarification:** Working closely with the client to define, clarify, and assess the feasibility of project requirements.
* **Team Leadership (Basic Project Management):** Assembled and coordinated a small team (UI designer, front-end developer, back-end developer) and made crucial project decisions to ensure the initial demo's success.
* **Strategic Advising:** Provided insights and guidance to the client on necessary considerations for future investment and scalability.

## 🚀 Getting Started (Conceptual)

While this repository primarily serves as a project showcase, a conceptual guide for setting up the environment for potential contributors or for a local demo would involve:

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YourUsername/HalalCash.git](https://github.com/MBayezid/HalalCash.git)
    ```
2.  **Open in Android Studio:** Import the project into Android Studio.
3.  **Backend Setup:** (Conceptual, as backend code might be separate) Set up the Laravel backend and database as per its own documentation.
4.  **Configure API Endpoints:** Update the Android app's configuration to point to your backend API.
5.  **Build and Run:** Build and run the application on an Android emulator or device.

*(Note: Specific backend setup instructions would reside in the backend repository.)*

## 🤝 Contributing

We welcome contributions! If you're interested in contributing to Halal Cash, please fork the repository and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.

**Team Members:**
* **[Musanna Bayezid]** - Front-End Mobile Developer & Project Coordinator
* [MD. Khalid Hasan] - UI/UX Designer
* [Medhi Hasan] - Additional Front-End Developer
* [Chayan Roy] - Back-End Developer

## 📄 License

This project is licensed under the MIT License - see the `LICENSE` file for details.
