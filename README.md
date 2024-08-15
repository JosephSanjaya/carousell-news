# Carousell News

Carousell News is an Android application designed to deliver the latest news with a clean and modern user interface. This project leverages a multi-module architecture to ensure scalability and maintainability.

## Features

- **Gradle Conventions**: Utilizes Gradle conventions for easier extension and minimal duplication in a multi-module architecture.
- **UI**: Built using Jetpack Compose for a modern and responsive user interface.
- **Networking**: Implements Ktorfit and Ktor for efficient network operations.
- **Dependency Injection**: Uses Dagger Hilt for dependency injection.
- **Lifecycle Management**: Integrates Jetpack Lifecycle components.
- **Architecture**: Follows the MVI pattern in ViewModel and clean architecture principles for package separation.
- **Mockk, Turbine, & Kotest**: Use mockk, turbine, and kotest as modern test framework
> Notes: for kotest we need to install kotest plugin to recognize test files.
  
## Module Structure
The project is organized into the following modules:

- **app**: The main application module that ties everything together.
- **convention**: Contains Gradle conventions for easier extension and minimal duplication across modules.
- **core**: Houses core functionalities shared across different layers (data, domain, presentation). This includes core HTTP client configurations for Ktorfit & Ktor, Compose themes, and other shared utilities.
- **feature:news:** The main feature module responsible for the news functionality.


## Getting Started

### Prerequisites

- Android Studio Koala Patch 2
- Kotlin 2.0
- Gradle 8.10
- Java 21

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/JosephSanjaya/carousell-news.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.

### Building the App

You can build the app manually from Android Studio after fulfilling the prerequisites:

1. Connect an Android device or start an emulator.
2. Click on the "Run" button in Android Studio.

For a faster approach, you can trigger the Android CI/CD job from the GitHub Actions section:

1. Go to the GitHub repository.
2. Navigate to the "Actions" tab.
3. Select the "Android CI/CD" workflow.
4. Click on "Run workflow" to trigger the build process.
5. Get apk from artifact section

## Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Ktor](https://ktor.io/)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Jetpack Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
