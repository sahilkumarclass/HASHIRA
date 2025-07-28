# HASHIRA: Secret Sharing Assignment

## Overview

HASHIRA is a Java project that demonstrates secret reconstruction using Lagrange interpolation, a fundamental concept in secret sharing schemes such as Shamir's Secret Sharing. The project reads secret shares from JSON files, reconstructs the original secret, and prints the result.

## Features
- Reads secret shares from JSON files
- Supports shares in different bases (binary, octal, decimal, hexadecimal, etc.)
- Reconstructs the secret using Lagrange interpolation at zero
- Example test cases provided

## Requirements
- Java 23 or higher
- Maven (for dependency management and building)

## Dependencies
- [Jackson Databind](https://github.com/FasterXML/jackson-databind) (for JSON parsing)

Dependencies are managed via `pom.xml` and will be installed automatically by Maven.

## Project Structure
```
HASHIRA/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sahilkumar/
│                   ├── Main.java
│                   ├── SecretSharingAssignment.java
│                   ├── test1.json
│                   └── test2.json
```

## Usage

### 1. Build the Project
```
mvn clean compile
```

### 2. Run the Secret Reconstruction
You can run the main class using Maven:
```
mvn exec:java -Dexec.mainClass="com.sahilkumar.SecretSharingAssignment"
```
Or compile and run using Java directly:
```
javac -cp .:path/to/jackson-databind-2.15.2.jar src/main/java/com/sahilkumar/SecretSharingAssignment.java
java -cp .:path/to/jackson-databind-2.15.2.jar com.sahilkumar.SecretSharingAssignment
```

### 3. Example Input
Example of a share file (`test1.json`):
```json
{
  "keys": { "n": 4, "k": 3 },
  "1": { "base": "10", "value": "4" },
  "2": { "base": "2", "value": "111" },
  "3": { "base": "10", "value": "12" },
  "6": { "base": "4", "value": "213" }
}
```

### 4. Example Output
```
Secret for test case 1: <reconstructed_secret_1>
Secret for test case 2: <reconstructed_secret_2>
```

## How It Works
- The program reads the JSON files containing the shares.
- Each share consists of an x-coordinate (the key), a base, and a value.
- The program decodes each value to a BigInteger using the specified base.
- Using Lagrange interpolation, it reconstructs the secret (the y-value at x=0).

## Customization
- To use your own shares, create a JSON file in the same format as `test1.json` or `test2.json` and update the file paths in `SecretSharingAssignment.java`.

## License
This project is for educational purposes. 
