# Java Spring Boot application for receiving RESTful calls for BioID Web Service gRPC API

## Overview

The **BioID RestGrpcForwarder** project is a Java Spring Boot application that receives REST calls and forwards these calls 
to the gRPC endpoint of the BioID Web Service 3. 

This approach allows you to utilize the advantages of gRPC (e.g. low latency and protocol buffer) while providing a 
RESTful API that is used by your client app(s).

To get a first impression of how our new biometric services work, you can try them out on our [BioID Playground][playground].

## Technologies

- Spring Boot
- RESTful API
- gRPC
- Java

## Project structure

The main source code of the project is located in the src/main/java/com/bws/restgrpcforwarder folder.
This is the standard project structure in a Spring Boot application.

- `auth/` - Contains custom authentication handlers that manage user authentication and authorization.
- `config/`
  - `GrpcClientConfig` - The Java configuration class for gRPC API endpoint properties.
  - `SecurityConfig` - It handles API key based authentication for the rest controller in the application. Currently set up for [LivenessDetection][liveness], [PhotoVerify][photoverify], and [VideoLivenessDetection][videoliveness].
- `controllers/` - Contains the controllers for the REST API endpoints.
- `datatypes/` - Data types represent the data structures with which the application works.
- `grpc/` - Includes the configured gRPC clients with methods for communicating with the BWS API via gRPC, as well as a converter for transforming gRPC metadata into HTTP headers.
- `security/` - Contains a utility class that generates JWT tokens with specified claims and expiration time, and applies these tokens to gRPC request metadata for authentication.
- `RestGrpcApplication.java` - This is the main entry point of the application.
- `/proto/` - Contains Protobuf definitions used for defining the data structures and bws service interfaces for gRPC communication.
- `/resources/application.properties` - This file is for application-specific configurations in a Spring Boot app.

## Get Started

This application works as a service and accepts REST requests, extracts e.g. input images (encoded as Base64 strings) from these requests,
and converts them into byte arrays. These images as byte arrays are then sent to the BioID Web Service (BWS) gRPC endpoint using a gRPC client.
The response from the BWS is returned to this service via gRPC and then used as the response for the REST call.

Depending on which gRPC API is used, additional parameters can also be transferred.

If you want to use your images or videos, you can convert them to or from a base64 encoded string using an online service like [base64.guru].
This service can handle conversions in both directions. Any other service can also be used for the conversion.

> [!IMPORTANT]
> Please use **base64** and not **base64url**.

> #### Before starting the service, follow these steps
>
> - You need a **BioID Account** with a **confirmed** email address. If you don’t have one, [create a BioID account][bioidaccountregister].
> - You can create a free [trial subscription][trial] of the BioID Web Service (BWS) once you've created your BioID account.
> - After you have signed in to the BWS Portal and created the trial subscription with the help of a wizard, you still need to create a BWS 3 client.
> - The client can be created with the help of a creation wizard.
> - If you have created a client, click on `Show client keys` to open the dialog box that displays the `ClientId` and `Keys` for your client.

>
> **The ClientId and Key will be explained in detail later on where to insert them.**

### Installation

It is assumed that Java is already installed. You can verify this by typing `java --version` in the command line.
A Maven wrapper is used in the project so that all necessary components are already included in the project.

#### 1. Clone the repository

- **Linux:**

  ```bash
  git clone https://github.com/BioID-GmbH/RestGrpcForwarder.git
  ```

- **Windows:**

  ```cmd
  git clone https://github.com/BioID-GmbH/RestGrpcForwarder.git
  ```

#### 2. Navigate to the project folder and build the java spring app

- **Linux:**

  ```bash
  chmod +x mvnw
  ./mvnw clean install
  ```

- **Windows:**

  ```cmd
  mvnw.cmd clean install
    ```

#### 3. Configure the application

Add your BWS gRPC clientId and key to the `/resources/application.properties` file to enable communication with our BWS. Refer to the instructions above to obtain these credentials. Set the `clientId` under `grpcApi.clientId` and the `access` key under `grpcApi.accessKey`. You can also configure the port on which the application runs by setting your port under `server.port=your port`.

> [!IMPORTANT]
> The BWS endpoint must be entered without **http://** under `grpcApi.endpoint` in application.properties.

#### 4. Launch the application

- **Linux:**

  ```bash
  ./mvnw spring-boot:run
  ```

- **Windows:**

  ```cmd
  mvnw.cmd spring-boot:run
  ```

### Example endpoints

*The current version of the app includes three new BWS APIs: [LivenessDetection][liveness], [PhotoVerify][photoverify]
and [VideoLivenessDetection][videoliveness].*

###### To test our BWS APIs, you can use tools such as `Postman`.

[base64.guru]: https://base64.guru/ "Base64 String online converter"
[bioidaccountregister]: https://account.bioid.com/Account/Register "Register a BioID account"
[trial]: https://bwsportal.bioid.com/ "Create a free trial subscription"
[bwsportal]: https://bwsportal.bioid.com "BWS Portal"
[liveness]: https://developer.bioid.com/bws/grpc/livenessdetection/ "Presentation attack detection."
[photoverify]: https://developer.bioid.com/bws/grpc/photoverify/ "PhotoVerify"
[videoliveness]: https://developer.bioid.com/bws/grpc/videolivenessdetection/ "Presentation attack detection in videos."
[playground]: https://playground.bioid.com "BioID Playground"
