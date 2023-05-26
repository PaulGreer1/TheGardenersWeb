## Overview of the server's request-response process

The Gateway class simulates the reception of HTTP requests from clients, and invokes the handleRequest() method defined in the Handler class. The Gateway object also creates the Handler object, which creates other components such as a Registrar which stores request and response data for use by various components throughout the process. The Registrar also manages the notification of event handlers as user interface (UI) events occur. The Controller manages the registration of its event handlers for UI events.

![Request_response_sequence_diagram](https://github.com/PaulGreer1/TheGardenersWeb/blob/main/REQUEST_RESPONSE_SEQUENCE_DIAGRAM.png)
Click image to enlarge

### Part 1: Simulating the HTTP Gateway

#### 1. UI events
In this application framework, a UI event is deemed to have occurred when a client request is received from the UI. In a live environment, the input data for an HTTP request come from the client UI. However, since we are developing and testing the system locally before uploading to the live server, we create an APIGatewayV2HTTPEvent object and an APIGatewayV2HTTPResponse object to simulate the initial stages of a request-response server process.

#### 2. Create an HTTP event object
The APIGatewayV2HTTPEvent object is used to carry the input data. Later, when the Gateway object's main() method is executed, this event object will be passed to the Handler object's handleRequest() method (at step 10).

#### 3. Create the Handler object
The Handler class defines the handleRequest() method. Later we will see how this method takes the event object and makes the event data available to other components of the request-response process.

#### 4. Create the Registrar object
The relationship between the event handlers (defined in the Controller class) and the events for which they may register, is many-to-many. That is, each handler may register for zero or more events, and each event may invoke zero or more handlers. When a UI event occurs, the Registrar's notify() method is called. This method retrieves and executes the event handlers which are registered for that event.

The Registrar also stores the event request data taken from the event object. These event data are passed to the event handlers as they are being executed by the notify() method.

#### 5. Create the Controller object
The Controller class defines the event handler methods which are registered for UI events. The event handlers themselves are implementations of the EventHandler interface, which is defined in the same file as the Controller class.

#### 6. Registering event handlers for events
The 'event-registration-callback' mechanism is a simple but powerful software design pattern in computer science. The Registrar requires no manual updates in order to add handlers to its list of callback methods. Event handlers are registered for event notifications by calling the Registrar's add() method, and deregistered by calling the Registrar's delete() method. All this is done without the Registrar needing to know anything at all about the event handlers which utilise its services.

The Registrar adds and deletes event handlers and their events to and from a HashMap< EventHandler, HashSet< String > >. The EventHandler is a key to the list of events for which it is registered. It is the HashSet< String > which holds all the events for which a particular handler is registered. Note that a 'set' is used to ensure that any particular event in a particular event handler's list is unique, and thus will not cause its event handler to be executed more than once for each occurrence of the event.

References to event handlers are defined in the Controller. During the construction of the Controller object, event handlers and the events for which they are being registered, are passed to the Registrar's add() method.

The event handlers all have the same signature as that given in the EventHandler interface (found at the top of Controller.java). The EventHandler interface provides a common signature for all the event handlers, and the implementations (defined in the Controller class) provide the executable bodies.

#### 8. Simulating connection requests
By this time, the Handler's constructor has returned a Handler object to the Gateway, and so the Gateway is now ready to simulate the initial request to the server by calling the Handler's handleRequest() method. The event data is passed to handleRequest(). Everything that happens after this is exactly the same as it is on the live server.

The Gateway class simulates an HTTP request-response gateway by using the APIGatewayV2HTTPEvent and APIGatewayV2HTTPResponse classes to define 'event' and 'response' objects respectively.

### Part 2: Handling requests and returning responses

#### 9. Event names
A HashMap< String, String > named queryStringParameters is populated with event data and passed to the event object's setQueryStringParameters() method. The event object's request data include a pre-defined event name. For example, 'event=INSERT_GARDEN'. In handleRequest(), this event name is passed to the Registrar's notify() method which will use it to search the event lists of registered event handlers. Other parameters depend on the function currently being carried out (see example requests in Gateway.java).

#### 10. Overview of request handling
The main() method invokes the Handler's handleRequest() method. The HTTP event object holding the request data is passed to handleRequest(). An APIGatewayV2HTTPResponse object named 'response' is defined. Later, after the event handler has been executed, any response data will be available from the Registrar. The Handler's handleRequest() method will get these data from the Registrar, and set the body of the response object. The response object will then be returned to the Gateway.

#### 11. Request and response data
As well as keeping track of event registrations, the Registrar also has two data stores, each in the form of a HashMap< String, String >. They are called queryStringParameters and dataStore. These HashMaps enable us to save request and response data to the Registrar. Depending on which stage of the execution sequence we're at, these data are available to any component which has a reference to the Registrar object. Request data are passed to registered event handlers when the Registrar notifies them about the event which has occurred. Response data are accessed by the handleRequest() method when it is time to respond to the Gateway.

#### 12. Notifying event handlers of events
The handleRequest() method calls the Registrar's notify() method, and passes the event object to it. The notify() method notifies all registered event handlers that an event has occurred. The notify() method iterates through the event handlers in the eventHandlers HashMap, searching each event handler's event list to see whether the handler is registered for the event. All handlers registered for the event are executed.

#### 13. Execution of the event handlers
Each event handler accesses the database. The 'input' parameter of an event handler is a HashMap< String, String > which holds the request data. These data are used as parameters in prepared SQL statements to access the database, and also as simple String variables to build specific SQL statements. Regular expressions are used to ensure that only pre-approved input is accepted. Prepared statements and regular expressions together prevent SQL injection.

#### 14. Types of database event handler
There are two types of database EventHandler - readers and writers. Readers retrieve tables of data which are saved to the Registrar's dataStore variable. Both readers and writers return booleans to the registrar.

#### 15. Data returned from event handlers
Any data that event handlers retrieve from the database are saved to the Registrar's dataStore HashMap. The keys of the dataStore HashMap are named after the event handler names. These data are available to any other component which keeps a reference to the Registrar object.

#### 16. The boolean return value of event handlers
A boolean is returned by all event handlers. This can be used to indicate the success or failure of an event handler.

#### 17. Retrieval of data from the response object
The Handler's handleRequest() method retrieves the data which were stored in the Registrar's dataStore by the event handlers which were executed by notify(). The handleRequest() method builds a string from the data and saves it to the body of the response object. In order to get at the returned data, the recipient needs to split this string on the hash characters ('#') to retrieve the respective event handler tables, and then split on the pipe characters ('|') to separate the rows of the individual tables.

#### 18. Passing the server's response to the Gateway
The handleRequest() method returns the response object to the Gateway.
