package com.strongcoffee.tgw;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class ControllerTest {

	Registrar registrar = new Registrar();
	Controller controller = new Controller( registrar );
	HashMap<String, String> queryStringParameters = new HashMap<String, String>();

	/**
	 *
	 * @InformalPostCondition: The number of rows in the garden table is incremented by 1.
	 * @FormalPostCondition: n(newGardens) = n(oldGardens) + 1
	 * @InformalPostCondition: There exists a row in the garden table with a garden_id that is different from
	 * any of the garden_ids that existed before the insertion.
	 * @FormalPostCondition: E i in newIds [ A j in oldIds [ j != i ] ]
	 * newId = newIds DIFF oldIds
	 * @FormalPostCondition: newIds = oldIds U newId
	 */
	@Test
	public void insertGardenTest() {

		// Before the insert, get the number of rows in the garden table.
		//EventHandler getGardens = controller.getGardens;
		queryStringParameters.put( "col_name", "garden_id" );
		controller.getGardens.execute( queryStringParameters );
		int sizeBefore = controller.gardens.size();

		// Before inserting the new row, make a copy of the gardens table.
		ArrayList<String> gardensCopy = new ArrayList<>();
		gardensCopy = controller.gardens;

		//
		String id1 = "";
		ArrayList<Integer> set1 = new ArrayList<>();
		for( int i = 0; i < controller.gardens.size(); i++ ) {
			id1 = controller.gardens.get(i).split("\\|")[0];
			set1.add( Integer.valueOf( id1 ) );
		}
		System.out.println( set1 );

		// Insert a row into the gardens table.
		//EventHandler insertGarden = controller.insertGarden;
		queryStringParameters.put("ui_event", "INSERT_GARDEN");
		queryStringParameters.put("garden_name", "Janie's Garden");
		queryStringParameters.put("description", "A garden for little birds.");
		controller.insertGarden.execute( queryStringParameters );

		// After the insert, get the number of rows in the garden table.
		controller.getGardens.execute( queryStringParameters );
		int sizeAfter = controller.gardens.size();

		System.out.println( sizeAfter + " | " + sizeBefore );

		assertTrue( sizeAfter == sizeBefore + 1 );

		//
		String id2 = "";
		ArrayList<Integer> set2 = new ArrayList<>();
		for( int i = 0; i < controller.gardens.size(); i++ ) {
			id2 = controller.gardens.get(i).split( "\\|" )[0];
			set2.add( Integer.valueOf( id2 ) );
		}
		System.out.println( set2 );

		System.out.println( TgwUtils.getDifference( set1, set2 ) );

		assertTrue( TgwUtils.getDifference( set1, set2 ).size() == 1 );

		//
		for( int i = 0; i < TgwUtils.getDifference( set1, set2 ).size(); i++ ) {
			assertTrue( ! set1.contains( TgwUtils.getDifference( set1, set2 ).get(i) ) );
		}

	}

}

/*
SELECT * FROM garden;

DELETE FROM garden WHERE garden_id IN (1,2,7,8);

*/

/*
In controller, gardens put into ArrayList so test can access them. resultSets do not last beyond con.close(). Q: How would you deal
with large lists in small memory? A: For tests requiring only ArrayList sizes, we could scale down all the ArrayLists
involved by a common factor.
 */

/**
 *
 * @InformalPostCondition: The number of rows in the garden table is decremented by 1.
 * @FormalPostCondition: n(newIds) = n(oldIds) - 1
 * @InformalPostCondition: There existed a row in the old garden table with a garden_id that is different from
 * any of the garden_ids that exist in the new table.
 * @FormalPostCondition: E i in oldIds [ A j in newIds [ j != i ] ]
 * @FormalPostCondition: newIds = oldIds DIFF deletedId
 */

/*
To get the size of a ResultSet:

Do a SELECT COUNT(*) FROM ... query,

OR

int size = 0;
if( rs != null )
{
	rs.last();              // moves cursor to the last row
	size = rs.getRow();     // get row id
}
*/