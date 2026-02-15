# DiscogsChallenge

This is an Android application that demonstrates modern Android development best practices. It's a multi-module project that uses a clean architecture approach to search for artists and view their albums from the Discogs API.

## ✨ Features

*   **Artist Search:** Search for artists in the Discogs database.
*   **Album Viewer:** View the albums of a selected artist in a grid.
*   **Album Filtering:** Filter albums by year.

## 📸 Screenshots / Recording

![Demo de la App](/assets/demo.gif)


## 🚀 Tech Stack

*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for building a declarative and modern UI.
*   **Architecture:** [MVI (Model-View-Intent)](https://orbit-mvi.org/) with [Orbit](https://orbit-mvi.org/) for predictable state management.
*   **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for managing dependencies.
*   **Networking:** [Ktor](https://ktor.io/) for making network requests to the [Discogs API](https://www.discogs.com/developers/).
*   **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for asynchronously loading images.
*   **Navigation:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for navigating between screens.
*   **Code Quality:**
    *   [KtLint](https://ktlint.github.io/): A static code analysis tool to ensure consistent and idiomatic Kotlin code.
    *   [Detekt](https://detekt.dev/): A static code analysis tool for Kotlin that provides a wide range of rule sets to check for code smells, complexity, and potential bugs.
*   **Test Coverage:** [JaCoCo](https://www.jacoco.org/jacoco/): A code coverage tool that helps to track the percentage of code that is covered by unit tests.
*   **Dependency Management:** [Version Catalog](https://docs.gradle.org/current/userguide/platforms.html): A centralized way to manage dependencies and their versions across all modules.
*   **Testing:**
    *   [JUnit](https://junit.org/junit5/) for unit testing.
    *   [MockK](https://mockk.io/) for mocking objects in tests.
    *   [Turbine](https://github.com/cashapp/turbine) for testing `Flow`s.
    *   [Ktor Client Mock](https://ktor.io/docs/client-testing.html) for testing network requests.
    *   Espresso and Compose UI Tests for instrumented tests.

## 🏗️ Project Structure

The project is organized into multiple modules to promote separation of concerns and code reusability.

*   `app`: The main application module that brings everything together.
*   `core`: Contains shared utilities, base classes, and extension functions used across the project.
*   `data`: Handles data sources, both remote (from the Discogs API) and local (if any). It contains the repository implementation.
*   `design_system`: A library of reusable UI components and themes to ensure a consistent look and feel throughout the app.
*   `domain`: Contains the business logic of the application, including use cases and repository interfaces.
*   `build_logic`: Contains custom Gradle plugins and build-related logic to manage dependencies and configurations in a centralized way.

## ⚙️ Configuration

To build and run the project, you need to provide an API key for the Discogs API.

1.  Create a file named `local.properties` in the root of the project.
2.  Add the following line to the `local.properties` file, replacing `YOUR_DISCOGS_API_KEY` with your actual key:

    ```
    DISCOGS_API_KEY="YOUR_DISCOGS_API_KEY"
    ```

## 🧠 Analysis and Development Process

The development process for this project followed these steps:

1.  **Analysis:** The project started with an analysis of the Discogs API to understand its capabilities and data structures.
2.  **Architecture:** A multi-module clean architecture was chosen to ensure a separation of concerns, scalability, and testability.
3.  **Development:** The development process was iterative, starting with the `data` layer, then the `domain` layer, and finally the `app` (UI) layer. This allowed for each layer to be built and tested independently.

## 🏛️ Architecture

The project follows a multi-module clean architecture, which separates the code into the following layers:

*   **Domain Layer:** This layer contains the core business logic of the application. It defines the use cases and the repository interface, but it doesn't know any implementation details.
*   **Data Layer:** This layer is responsible for providing data to the domain layer. It contains the implementation of the repository interface, which fetches data from the Discogs API.
*   **Presentation (UI) Layer:** This layer is responsible for displaying the data to the user. It uses Jetpack Compose and follows the MVI pattern with Orbit to manage the state of the UI.

This architecture was chosen for the following reasons:

*   **Separation of Concerns:** Each layer has a specific responsibility, which makes the code easier to understand and maintain.
*   **Testability:** Each layer can be tested independently, which makes it easier to write and run tests.
*   **Scalability:** The architecture is scalable, which means that new features can be added easily without affecting the existing code.

## 🛠️ How to Build

To build the project, you can use Android Studio or the command line:

```bash
./gradlew assembleDebug
```

## 🧪 Code Quality and Testing

### KtLint

To run KtLint and check for code style violations:

```bash
./gradlew ktlintCheck
```

To automatically fix any fixable issues:

```bash
./gradlew ktlintFormat
```

### Detekt

To run Detekt and check for code smells:

```bash
./gradlew detekt
```

### JaCoCo

To run unit tests and generate a JaCoCo code coverage report:

```bash
./gradlew jacocoDebugReport
```

The report will be available in `app/build/reports/jacoco/jacocoDebugReport/html/index.html`.

### All Tests

To run all tests (unit and instrumented):

```bash
./gradlew check
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps to contribute:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and ensure that all tests pass.
4.  Run the code quality checks to ensure your code adheres to the project's style guidelines.
5.  Submit a pull request using the provided `pull_request_template.md`.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 📚 API Reference

The following endpoints from the Discogs API are used in this project:

*   `GET /database/search`: Search for artists.
*   `GET /artists/{artist_id}`: Get information about a specific artist.
*   `GET /artists/{artist_id}/releases`: Get a list of an artist's releases.
