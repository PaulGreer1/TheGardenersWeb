package com.strongcoffee.tgw;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public class TgwUtils {

	public TgwUtils() {}

	/**
	 * Finds the integer which is not in a given set of integers, and which is as low as possible.
	 * Assumes all integers in ints are greater than zero.
	 * @param ints .
	 * @return .
	 */
	public static int createId( ArrayList<Integer> ints ) {

		Integer newUnique = 0;
		Collections.sort( ints );
		for( Integer num : ints ) {
			if( ( num - newUnique ) > 1 ) {
				newUnique = newUnique + 1;
				break;
			} else {
				if( newUnique == ints.size() ) {
					break;
				} else {
					newUnique++;
				}
			}
		}

		if( ints.contains( newUnique ) || newUnique == 0 ) {
			newUnique++;
		}

		return newUnique;

	}

	/**
	 * Get the difference between two sets.
	 * An algorithm for a set difference function. That is, finds all the elements in longerSet which are not in shorterSet.
	 * <pre>
	 *
	 *      Set getDifference( Set longerSet, Set shorterSet ) {
	 *
	 *      Set difference <- ()
	 *
	 *      foreach l in longerSet {
	 *      Boolean found <- false
	 *          foreach s in shorterSet {       // Search for l in shorterSet.
	 *              if( l = s ) {
	 *                  found <- true
	 *                  break
	 *              }
	 *          }
	 *          if( ! found ) {
	 *              difference.put(l)
	 *          }
	 *      }
	 *
	 *      return difference
	 *  }
	 * </pre>
	 * @param set1 An ArrayList of Integer.
	 * @param set2 An ArrayList of Integer.
	 * @return An ArrayList of Integer holding the difference between set1 and set2.
	 */
	public static ArrayList<Integer> getDifference( ArrayList<Integer> set1, ArrayList<Integer> set2 ) {

		// The algorithm below requires the sets to be ordered by size, the larger being on the outer loop.
		ArrayList<Integer> longerSet;
		ArrayList<Integer> shorterSet;
		if( set1.size() >= set2.size() ) {
			longerSet = set1;
			shorterSet = set2;
		} else {
			longerSet = set2;
			shorterSet = set1;
		}

		ArrayList<Integer> difference = new ArrayList<>();

		for( int i = 0; i < longerSet.size(); i++ ) {
			boolean found = false;
			for( int j = 0; j < shorterSet.size(); j++ ) {
				if( longerSet.get(i) == shorterSet.get(j) ) {
					found = true;
					break;
				}
			}
			if( ! found ) {
				difference.add( longerSet.get(i) );
			}

		}

		return difference;
	}
}
