# Swift Code API

A Spring Boot API for querying and managing SWIFT/BIC codes using a MySQL database in a Dockerized environment. Data provided by Remitly.

## Technologies Used

* Java 21
* Spring Boot
* Spring Data JPA
* MySQL
* Docker & Docker Compose

## Getting Started
🚧 **currently working on contenarization** 🚧

### Prerequisites

* Docker
* Docker Compose

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/swift-code-api.git
   cd swift-code-api
   ```

2. Start the containers:

   ```bash
   docker-compose up --build
   ```

3. The API will be accessible at:

   ```
   http://localhost:8080
   ```

## API Endpoints

### 1. Get SWIFT Code Details
**GET** `/v1/swift-codes/{swift-code}`  
Retrieves details of a single SWIFT code.

- If the code belongs to a headquarter, the response includes a list of branch details.
- If it's a branch, only its own details are returned.

**Response (Headquarter):**
```json
{
  "address": "string",
  "bankName": "string",
  "countryISO2": "string",
  "countryName": "string",
  "isHeadquarter": true,
  "swiftCode": "string",
  "branches": [
    {
      "address": "string",
      "bankName": "string",
      "countryISO2": "string",
      "isHeadquarter": false,
      "swiftCode": "string"
    }
  ]
}
```
**Response (Branch):**
```json
{
  "address": "string",
  "bankName": "string",
  "countryISO2": "string",
  "countryName": "string",
  "isHeadquarter": false,
  "swiftCode": "string"
}
```
### 2. Get All SWIFT Codes by Country
**GET** `/v1/swift-codes/country/{countryISO2code}`
Returns all SWIFT codes (headquarters and branches) for the given country ISO2 code.

**Response:**
```json
{
  "countryISO2": "string",
  "countryName": "string",
  "swiftCodes": [
    {
      "address": "string",
      "bankName": "string",
      "countryISO2": "string",
      "isHeadquarter": true,
      "swiftCode": "string"
    },
    {
      "address": "string",
      "bankName": "string",
      "countryISO2": "string",
      "isHeadquarter": false,
      "swiftCode": "string"
    }
  ]
}
```
### 3. Add a New SWIFT Code
**POST** `/v1/swift-codes`
Adds a new SWIFT code entry to the database.

**Request:**
```json
{
  "address": "string",
  "bankName": "string",
  "countryISO2": "string",
  "countryName": "string",
  "isHeadquarter": true,
  "swiftCode": "string"
}
```

**Response:**
```json
{
  "message": "SWIFT code successfully added"
}
```

### 4. Delete a SWIFT Code
**DELETE** `/v1/swift-codes/{swift-code}`
Deletes a SWIFT code by its identifier.

**Response:**
```json
{
  "message": "SWIFT code deleted successfully"
}
```








