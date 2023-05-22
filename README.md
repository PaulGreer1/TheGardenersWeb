### TheGardenersWeb

![Request_response_sequence_diagram](https://github.com/PaulGreer1/TheGardenersWeb/blob/main/REQUEST_RESPONSE_SEQUENCE_DIAGRAM.png)
Click image to enlarge

#### 4. The role of the Registrar
A UI event occurs when a request comes in from the UI, passes through the Gateway and on to the Handler. The Handler invokes the Registrar's notify() method, passing the name of the event. The relationship between the event handlers and the events for which they are registered, is many-to-many. That is, each handler may register for zero or more events, and each event may invoke zero or more handlers. The Registrar adds and deletes event handlers and their events to and from a HashMap<EventHandler, HashSet<String>>. When an event occurs, the Registrar's notify() method retrieves and executes the event handlers which have that event listed in its HashSet of events.

The Registrar also stores request data, and passes to registered event handlers as they are being executed in the notify() method.
