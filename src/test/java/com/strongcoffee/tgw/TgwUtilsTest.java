package com.strongcoffee.tgw;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TgwUtilsTest {

	/**
	 * The following conditions must hold after execution of getDifference().
	 * @InformalPostCondition: difference consists of all the elements in longerSet which are not
	 * in shorterSet.
	 */
	@Test
	public void getDifferenceTest() {

		//
		ArrayList<Integer> set1 = new ArrayList<>( Arrays.asList(1,2,3,4) );
		ArrayList<Integer> set2 = new ArrayList<>( Arrays.asList(11,2,3,4,5) );
		System.out.println( TgwUtils.getDifference( set1, set2 ) );

		//
		for( int i = 0; i < TgwUtils.getDifference( set1, set2 ).size(); i++ ) {
			assertTrue( ! set1.contains( TgwUtils.getDifference( set1, set2 ).get(i) ) );
		}
	}

	/**
	 * @Set: S = { The input ID set - a sorted set of n integers }
	 * @Set: D = { The set of differences between consecutive integers }
	 * The following conditions must hold after execution of createId().
	 * @FormalPostCondition: ( s_1 = 1 /\ A d in D [ d = 1 ] ) => w = s_n(S) + 1
	 * @FormalPostCondition: E d in D [ d > 1 ] => w <= s_n(S)
	 * @FormalPostCondition: ! E s in S [ s = w ]
	 * @FormalPostCondition: n(W) = n(S) + 1
	 * @FormalPostCondition: s_1 > 1 => w = 1
	 *
	 * <pre>
	 *
	 * Input set                    | New ID
	 * { }                          | 1
	 * { 1 }                        | 2
	 * { 12 }                       | 1
	 * { 1, 2, 3, 4, 5 }            | 6
	 * { 3, 4, 5, 6, 7 }            | 1
	 * { 4, 9 }                     | 1
	 * { 1, 4, 5, 6, 8, 9, 10 }     | 2
	 * { 4, 5, 9, 10, 14, 15 }      | 1
	 * </pre>
	 */
	@Test
	public void createIdTest() {

		// Test inputs.
		ArrayList< ArrayList< Integer > > listOfInputs = new ArrayList<>();
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList() ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 1 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 12 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 1, 2, 3, 4, 5 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 3, 4, 5, 6, 7 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 4, 9 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 1, 4, 5, 6, 8, 9, 10 ) ) );
		listOfInputs.add( new ArrayList< Integer >( Arrays.asList( 4, 5, 9, 10, 14, 15 ) ) );

		for( ArrayList< Integer > ids : listOfInputs ) {

			ArrayList<Integer> oldIds = new ArrayList<>( ids );

			int newId = TgwUtils.createId( ids );
			System.out.println( newId );
			assertTrue( ! oldIds.contains( newId ) );                  // ! E s in S [ s = w ]

			ids.add(newId);
			assertTrue(ids.size() == oldIds.size() + 1 );   // s_1 > 1 => w = 1

			if ( oldIds.size() > 0 ) {
				if ( oldIds.get(0) == 1 && this.allDiffsEqual1( this.getDiffs( oldIds ) ) ) {       // first element in oldIds = 1 /\ A d in D [ d = 1 ]
					assertTrue(newId == oldIds.get( oldIds.size() - 1 ) + 1 );            // w = s_n(S) + 1
				} else {                                                                            // E d in D [ d > 1 ]
					assertTrue(newId <= ids.get( ids.size() - 1 ) );                      // w <= s_n(S)
				}

				if ( oldIds.get(0) > 1 ) {
					assertTrue(newId == 1 );
				}
			}
		}
	}

	/**
	 * @param intArray .
	 * @return .
	 */
	private ArrayList<Integer> getDiffs( ArrayList<Integer> intArray ) {
		//
		ArrayList<Integer> diffs = new ArrayList<>();
		for( int i = 0; i < intArray.size() - 1; i++ ) {
			int diff = intArray.get(i+1) - intArray.get(i);
			diffs.add( diff );
		}

		return diffs;
	}

	/**
	 * @param diffs .
	 * @return .
	 */
	private boolean allDiffsEqual1( ArrayList<Integer> diffs ) {
		//
		boolean result = true;

		for( Integer diff : diffs ) {
			if( diff != 1 ) {
				result = false;
				break;
			}
		}

		return result;
	}
}
