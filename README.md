### TheGardenersWeb

![Request_response_sequence_diagram](https://github.com/PaulGreer1/TheGardenersWeb/blob/main/REQUEST_RESPONSE_SEQUENCE_DIAGRAM.png)
Click image to enlarge

#### 4. The role of the Registrar
A UI event occurs when a request comes from the UI, then passes through the Gateway and on to the Handler. The Handler invokes the Registrar's notify() method, passing the name of the event. The relationship between the event handlers and the events for which they are registered, is many-to-many. That is, each handler may register for zero or more events, and each event may invoke zero or more handlers. The Registrar adds and deletes event handlers and their events to and from a HashMap< EventHandler, HashSet< String > >. When an event occurs, the Registrar's notify() method retrieves and executes the event handlers which have that event listed in their HashSets of events.

The Registrar also stores request data which it passes to registered event handlers as they are being executed in the notify() method.

#### 6. Registering event handlers for events
References to Controller methods, each with the same signature as that given in the EventHandler interface (found at the top of Controller.java), are passed to the Registrar's add() method, along with the event for which the method is being registered. The add() method adds the event handler methods to a HashMap< EventHandler, HashSet< String > >. The HashSet< String > holds all the events for which the handler is registered. The EventHandler interface provides the event handler's signature, and the method implementation provides the body. A reference to the handler is stored in the HashMap. As request events occur, the Registrar's notify() method executes the relevant handlers via these references.

#### 8. Simulating connection requests
The Gateway class simulates an HTTP request-response gateway using APIGatewayV2HTTPEvent and APIGatewayV2HTTPResponse to define 'event' and 'response' objects respectively. The main() method uses a HashMap< String, String > to store request data. This HashMap is then passed to the event object's setQueryStringParameters() method, and the event object is then passed to the Handler's handleRequest() method.

#### 9. Event names
The event object's request data include a pre-defined event name. For example, 'event=INSERT_GARDEN'. In handleRequest(), this event name is passed to the Registrar's notify() method. Other parameters depend on the function currently being carried out (see example requests in Gateway.java).

#### 10. Overview of request handling
The main() method invokes the Handler's handleRequest() method. The HTTP event object holding the request data is passed to handleRequest(). An APIGatewayV2HTTPResponse object 'response' is defined. After event handler has executed, response data is available in Registrar. Handler's handleRequest() method gets the data from Registrar and sets body of response object. The response object is then returned to the Gateway.

#### 11. Request and response data
As well as keeping track of event registrations, the Registrar also has two data stores, each in the form of a HashMap< String, String >, called queryStringParameters and dataStore. These enable us to save request and response data to the Registrar. Depending on which stage of the execution sequence we're at, these data are available to any component which has a reference to the Registrar object. The request data are passed to registered event handlers when the Registrar notifies them about the event. The response data are accessed by the handleRequest() method when it is time to respond to the Gateway.

#### 12. Notifying event handlers of events
The Handler calls the Registrar's notify() method passing the event object. The Registrar notifies all registered event handlers that an event has occurred. The notify() method iterates through the event handlers in the eventHandlers HashMap searching each event handler's event list to see whether the handler is registered for the event. All handlers registered for the event are executed.

#### 13. Execution of the event handlers
Each event handler accesses the database. The 'input' parameter of an event handler is a HashMap< String, String > which holds the request data. This is used in prepared statements to access the database, and also to build SQL statements. Regular expressions are used to ensure pre-approved input. Prepared statements and regular expressions prevent SQL injection.

#### 14, 15, 16. Types of database event handler
There are two types of database EventHandler - readers and writers. Readers return tables of data which are saved in the Registrar's dataStore variable. Writers return booleans to the registrar.

#### 17.
The Handler's handleRequest() method retrieves the table of data from the Registrar's dataStore and saves it to the body of the response object.

#### 18.
The handleRequest() method returns the response object to the Gateway.
