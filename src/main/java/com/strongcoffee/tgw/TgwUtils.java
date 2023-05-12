package com.strongcoffee.tgw;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public class TgwUtils {

	public TgwUtils() {

	}

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

}
