package com.strongcoffee.tgw;

import org.junit.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Set: H = { The set of registered handlers }
 * @Set: V = { The set of events for a particular handler }
 */
public class RegistrarTest {

	/**
	 * For any particular event handler, the maximum number of registrations that the handler can have for a particular event, is 1.
	 * There is more than one possible algorithm. The pseudocode below gives one possibility.
	 * @InformalPostCondition: Each element in a particular handler's set of events is unique within that set.
	 * @FormalPostCondition: A h in H [ A v in V [ v not in { V DIFF { v } } ] ]
	 * @PseudoCode:
	 * <pre>
	 *  result1 <- true
	 *  for each eventHandler h in eventHandlers {
	 *      loop1BreakFlag <- false
	 *      for each event v in events {
	 *          el <- removeFirstElement() from events
	 *          if( el is in events ) {
	 *              result1 <- false
	 *              loop1BreakFlag <- true
	 *              break
	 *          }
	 *      }
	 *      if( loop1BreakFlag ) {
	 *          break
	 *      }
	 *  }
	 *
	 *  assertTrue( result1 )
	 * </pre>
	 *
	 * @InformalPostCondition: Each handler in the set of registered handlers is unique within that set.
	 * @FormalPostCondition: A h in H [ h not in { H DIFF { h } } ]
	 * @PseudoCode:
	 * <pre>
	 *  result2 <- true
	 *  for each eventHandler h in eventHandlers {
	 *      el <- removeFirstElement() from eventHandlers
	 *      if( el is in eventHandlers ) {
	 *          result2 <- false
	 *          break
	 *      }
	 *  }
	 *
	 *  assertTrue( result2 )
	 * </pre>
	 */
	@Test
	public void addTest() {
		Registrar registrar = new Registrar();
		Controller controller = new Controller( registrar );
		registrar.add( controller.insertGarden, "1", "INSERT_GARDEN" );
		registrar.add( controller.insertGarden, "2", "INSERT_GARDEN" );
		registrar.add( controller.insertGarden, "4", "INSERT_GARDEN" );
		int eventCount = 0;
		for( HashMap.Entry< EventHandler, HashSet< String > > entry : registrar.eventHandlers.entrySet() ) {
			//registrar.eventHandlers.get( entry.getKey() ).remove("INSERT_GARDEN" );
			for( String  event : registrar.eventHandlers.get( entry.getKey() ) ) {                 // size() is that of the set of events for this eventHandler.
				if( Objects.equals( event, "GET_GARDENS") ) {
					eventCount++;
				}
			}
		}
		assertTrue( eventCount == 0 || eventCount == 1 );
	}

	/**
	 *
	 */
	@Test
	public void notifyTest() {

	}

}

/*
Plain English can be enough. But mathematical expressions are less ambiguous and move us further towards
code - i.e. something that a machine can understand. But if you are convinced that you understand the system, then
you should be able to write effective tests starting from plain English post-conditions.

Post-conditions don't need to be restricted to conditions which need to hold after individual methods or functions.
It is perfectly fine to specify post-conditions which should hold after a sequence of actions has been executed. Use
sequence diagrams.

Post-conditions progress from a plain English statement, to a mathematical expression, to a code block with assert statements.
Sometimes it is possible to stop at the informal post-condition stage then code the tests. Other times it is
better to express some or oll of the sub-post-conditions formally to be certain.

*/
