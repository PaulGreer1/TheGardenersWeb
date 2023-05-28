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
	 * Any particular event handler can register only once for any particular event.
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
			for( String  event : registrar.eventHandlers.get( entry.getKey() ) ) {
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
