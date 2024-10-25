# Rest to Grpc Forwarder

## Overview

The **BioID RestGrpcForwarder** project is a Java Spring Boot application that serves as an interface between REST APIs and gRPC services.
The application is designed to receive requests from REST endpoints and forward them to gRPC services.
This approach allows leveraging the advantages of gRPC (such as low latency and protocol buffers) while providing a REST API externally.
To get a first impression of how our new services work, you can try them out on our [BioID Playground][playground].

## Technologies

- Spring Boot
- gRPC
- Restful
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
- `grpc/` - Includes the configured gRPC clients with methods for communicating with the BWS API via gRPC, as well as a converter for transforming  gRPC metadata into HTTP headers, along with a gRPC interceptor.
- `security/` - Contains a utility class that generates JWT tokens with specified claims and expiration time, and applies these tokens to gRPC request metadata for authentication.
- `RestGrpcApplication.java` - This is the main entry point of the application.
- `/proto/` - Contains Protobuf definitions used for defining the data structures and bws service interfaces for gRPC communication.
- `/resources/application.properties` - This file is for application-specific configurations in a Spring Boot app.

## Get Started

This web application functions as a service. It accepts REST requests, extracts input images from these requests,
and converts them into byte arrays. These byte arrays are then sent to the BWS gRPC service using a gRPC client.
In the sample application, it is assumed that the input images are encoded as Base64 strings.

If you want to use your images or videos, you can convert them to or from a base64 encoded string using an online service like [base64.guru].
This service can handle conversions in both directions.Any other service can also be used for the conversion.

> [!IMPORTANT]
> Please use **base64** and not **base64url**.

> #### Before starting the service, follow these steps
>
> - You need a **BioID Account** with a **confirmed** email address. If you don't have one [create BioID account][bioidaccountregister].
> - You can request a free [trial instance][trial] of the BioID Web Service (BWS) once you've created your BioID account.
> - Once you have received your trial access, log in to the [BWS Portal][bwsportal].
> - After logging in to the BWS portal, you will be given a trial subscription to bws. You should then create your own bws client
> to communicate with the bws service.  The client can be created using a creation wizard.
> - If you have created a Client, click on `Show Client Key` to open the dialog box that displays the `ClientId` and `Secret` for your Client.
>
> **The ClientId and Secret will be explained in detail later on where to insert them.**

### Installation

It is assumed that Java Development Kit (JDK) is already installed. You can verify this by typing `java --version` in the command line.
If not, you can download the [JDK][jdk] for your platform. Please use at least JDK version 22 or newer.
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

Add your BWS gRPC clientID and secret key to the `/resources/application.properties` file to enable communication with our BWS. Refer to the instructions above to obtain these credentials. Set the `clientId` under `grpcApi.clientId` and the `secret` key under `grpcApi.secret`. You can also configure the port on which the application runs by setting your port under `server.port=your port`.

> [!IMPORTANT]
> The BWS endpoint must be entered without **http://**  under `grpcApi.endpoint` in application.properties.
> Like: grpcApi.endpoint=localhost:5226

This application runs under HTTP.

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
[trial]: https://bwsportal.bioid.com/register "Register for a trial instance"
[bwsportal]: https://bwsportal.bioid.com "BWS Portal"
[liveness]: https://developer.bioid.com/bws/grpc/livenessdetection/ "Presentation attack detection."
[photoverify]: https://developer.bioid.com/bws/grpc/photoverify/ "PhotoVerify"
[videoliveness]: https://developer.bioid.com/bws/grpc/videolivenessdetection/ "Presentation attack detection in videos."
[playground]: https://playground.bioid.com "BioID Playground"
[jdk]: https://www.oracle.com/java/technologies/downloads/?er=221886#javasejdk "Java Development Kit"
