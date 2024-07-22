# ESCN Validator Client

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

`EscnClientFactory` is a Java client designed to interact with the European Student Card Router application (ESC-R) API. 
It provides functionality to validate ESCN (European Student Card Numbers) using a RESTful API endpoint. This class is part of a larger library aimed at simplifying integration with ESC services.

## Usage
### Creating an Instance of EscnClientFactory
You can create an instance of EscnClientFactory with the default host or a specified host.

#### Default Host
```java
EscnClientFactory factory = EscnClientFactory.create();
```
#### Specified Host
```java
EscnClientFactory factory = EscnClientFactory.create("http://your-custom-host.com");
```

### Validating an ESCN
To validate an ESCN, use the validateEscn method. This method throws IOException and URISyntaxException if an error occurs during the request.

```java
  String escn = "123456789012";
  CardVO card = factory.validateEscn(escn);
```

## Class Documentation
### EscnClientFactory
Factory class for creating instances of ESCN Client.

##### Methods
* `create()`: Creates a new instance of EscnClientFactory with the default host URL.
* `create(String host)`: Creates a new instance of EscnClientFactory with a specified host URL.
* `CardVO validateEscn(String escn)`: Validates the given ESCN and returns a CardVO object containing the validation result.