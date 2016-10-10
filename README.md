# Java Web Server

A simple multi-threaded web server written in Java and implementing the HTTP/1.1 specification.

##Requirements

* Java 8

## Building

Run the Maven `package` goal:

`mvn clean package`

This will produce an executable JAR in the `target` directory.

## Usage

Start the server with default settings:

`java -jar java-webserver.jar`

Start the server on a specific port:

`java -jar java-webserver.jar --port 80`

Specify an alternate docroot:

`java -jar java-webserver.jar --docroot /path/to/docroot`

Display all options and usage information:

`java -jar java-webserver.jar --help`

## Acknowledgements

* Favicon courtesy of [FamFamFam Silk icons collection](http://www.famfamfam.com/lab/icons/silk/), licensed under Creative Commons Attribution 3.0 License