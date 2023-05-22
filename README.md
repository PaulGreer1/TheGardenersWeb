### TheGardenersWeb

![Request_response_sequence_diagram](https://github.com/PaulGreer1/TheGardenersWeb/blob/main/REQUEST_RESPONSE_SEQUENCE_DIAGRAM.png)
Click image to enlarge

#### 4. The role of the Registrar
A UI event occurs when a request comes from the UI, then passes through the Gateway and on to the Handler. The Handler invokes the Registrar's notify() method, passing the name of the event. The relationship between the event handlers and the events for which they are registered, is many-to-many. That is, each handler may register for zero or more events, and each event may invoke zero or more handlers. The Registrar adds and deletes event handlers and their events to and from a HashMap<EventHandler, HashSet<String>>. When an event occurs, the Registrar's notify() method retrieves and executes the event handlers which have that event listed in its HashSet of events.

The Registrar also stores request data, and passes to registered event handlers as they are being executed in the notify() method.

#### 6. Registering event handlers for events
References to methods each with same signature as that given in the EventHandler interface. The method provides body, and named reference which can be stored and through which the body can be executed.

#### 8 Simulating connection requests
In main(), Scanner object is used on the standard input (System.in) to simulate a server listening for connection requests.

#### 9 Store data with the event object
queryStringInputParameters includes an event name, e.g. event=INSERT_GARDEN. In handleRequest(), this event name is passed to the Registrar's notify() method. Other parameters attached depending on requirements of request.

#### 10.
Pass the HTTP event object holding request data.

#### 11.
These data passed to registered event handlers when Registrar notifies them about event.

#### 12.
Registrar has everything it needs to notify all registered event handlers that this event.

#### 13.
Each event handler accesses the database.

#### 14.
Two types of DB EventHandler - readers and writers. Readers return data tables. These are saved in Registrar's dataStore. Writers return booleans.

#### 17.
Readers will have saved data in the Registrar's dataStore. The Handler retrieves this data and saves it to the response object.

#### 18.
Body has been set with 
