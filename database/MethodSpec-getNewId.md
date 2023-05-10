### PART 1: Theory

Many developers use the AUTO INCREMENT functions of their DBMS to generate entity identification numbers. This is not always suitable because, as rows are deleted, gaps are left in the id set.

The method specified below keeps the maximum number as low as possible. This would be useful for, say, a queueing system which requires a low maximum number and numbers consisting of only two or three digits. Once a number has been used, it is released back into the pool. Numbers with two or three digits can be quickly read on a screen or paper ticket. They can be easily remembered, easily spoken and heard via a loudspeaker, and thus can be quickly matched visually by the ticket holder.

This is the identification component of a seating area tracking system. There is another component which tracks the positions of users in a queue, but I've omitted this so that I can give a quick and simple demonstration.

#### Notation

Underscore attaches a subscript to denote an element's index.

n denotes set cardinality; e.g. n(B) is like a function which returns the cardinality of the set B.

A means "for all".

E means "there exists".

! means "not".

#### Sets

The input ID set is a sorted set of n integers:

`S = { s_1, s_2, ..., s_n(S) }`

The new ID set is a sorted set of n+1 integers:

`W = { s_1, s_2, ..., s_n(S), s_n(S)+1 }`

The set of differences between consecutive values in S:

`D = { s_i+1 - s_i | 1 <= i <= n(S)-1 }`

w is the newly generated ID.

#### Some logical components

NoGaps : There are no gaps:

`A d in D [ d = 1 ]`

NewIdEqualsLastIdPlusOne : The new ID is equal to the last ID in S, plus 1:

`w = s_n(S) + 1`

Gaps : There is at least one occurrence in S where the difference between two consecutive numbers is greater than 1:

`E d in D [ d > 1 ]`

NewIdLessThanHighestId : The new ID is less than the highest number in S:

`w < s_n(S)`

NewIdNotInS : The new ID w is not in S:

`! E s in S [ s = w ]`

OneMore : After the addition of a new ID to S, the cardinality of new S is equal to the cardinality of old S plus 1:

`n(W) = n(S) + 1`

`s_1 > 1 => w = 1`

#### A post-condition

Assembling the components above into a single post-condition, we have:

<pre>
A d in D [ d = 1 ] => w = s_n(S) + 1        // NoGaps implies NewIdEqualsLastIdPlusOne)
/\
E d in D [ d > 1 ] => w < s_n(S)            // (Gaps implies NewIdLessThanHighestId)
/\
! E s in S [ s = w ]                        // The new ID is not in the old ID set S
/\
n(W) = n(S) + 1                             // The new ID set W has exactly one more element than the old ID set S
</pre>

This is what must be asserted in our tests.

The following is a pseudocode walk-through. (Note that this may be different from the actual code we end up with):

<pre>
getNewId( S ) : Integer {
    i <- 1
    newId <- s_n(S) + 1             // If S is empty, then newId will be 1.
    for each s in S { // LOOP_1
        if i < n(S) {               // Avoids 'index out of range' exception.
            if s_i+1 - s_i > 1 {
                newId <- s_i + 1
                break out of LOOP_1
            }
        }
        i++
    }
    return newId
}
</pre>

We will test this with the following possible input sets:

<pre>
{ }
{ 1 }
{ 12 }                      // This case was not anticipated above.
{ 1, 2, 3, 4, 5 }
{ 3, 4, 5, 6, 7 }
{ 4, 9 }
{ 1, 4, 5, 6, 8, 9, 10 }
{ 4, 5, 9, 10, 14, 15 }
</pre>

We can see that our logic will fail for sets that have first element greater than 1. Add the following condition:

`s_1 > 1 => w = 1`

Develop our pseudocode:

<pre>
getNewId( S ) : Integer {
    if( n(S) > 0 /\ s_1 > 1 ) {         // If S is not empty, and the first element in S is greater than 1 ...
        new Id <- 1                     // ... then the new ID is 1.
    }
    else {
        i <- 1
        newId <- s_n(S) + 1             // Start by assuming there are no gaps. Also ensures that if S is empty, then the loop won't execute and newId will be 1.
        for each s in S { // LOOP_1
            if i < n(S) {               // Avoids 'index out of range' exception. Also,
                if s_i+1 - s_i > 1 {
                    newId <- s_i + 1
                    break out of LOOP_1
                }
            }
            i++
        }
    }
    return newId
}
</pre>

We need another method to find the set of differences.

This method also needs a post-condition. The sums of the corresponding elements of S and D are equal to the next element in S.

S = { 1, 2, 5, 7, 8 }
D = { 1, 3, 2, 1 }
    { 2, 5, 7, 8 }

A s in S [ A d in D [ s_i + d_i = s_i+1                ( 1 <= i <= n(S) - 1 )

If this is true then we have a valid set of differences.

So far we have been 'engineering' software. At present, the system is purely theoretical.

From the conditions identified above, we can form the following post-condition:

<pre>
A d in D [ d = 1 ] => w = s_n(S) + 1        // [1]
/\
E d in D [ d > 1 ] => w < s_n(S)            // [2]
/\
! E s in S [ s = w ]                        // [3]
/\
n(W) = n(S) + 1                             // [4]
/\
s_1 > 1 => w = 1                            // [5]

// [1] NoGaps implies NewIdEqualsLastIdPlusOne
// [2] Gaps implies NewIdLessThanHighestId
// [3] The new ID is not in the old ID set S
// [4] The new ID set W has exactly one more element than the old ID set S
// [5] The first element in S being greater than 1 implies that getNewId() should return 1 as the new ID.
</pre>

#### Tests

<pre>
gapsTests : Boolean {
    result = false
    if Gaps {
        result = assertTrue( Utils.getNewId(S) < the last element in S )
    }
    else { // NoGaps
        result = assertTrue( Utils.getNewId = the last element in S, plus 1 )
    }
    return result
}

newIdNotInSTest : Boolean {
    return assertTrue( S.contains.( Utils.getNewId(S) ) )
}

sSizePlusOneTest : Boolean {
    return assertTrue( Sets.union( S, { Utils.getNewId(S) } ).size() = S.size() + 1 )
}

sFirstElementIsOneTest : Boolean {
    result = false
    if the first element in S > 1 {
        result = assertTrue( Utils.getNewId(S) = 1 )
    }
    return result
}
</pre>


This example shows that even if a problem appears trivial, we should never start by attacking it with code. Instead, we should approach problems with reason and logic.

### PART 2: Practice













JavaScript:

* How to write unit tests.

* How to throw exceptions.

* What does '#' in front of a variable mean?
See section 'Using Object-oriented Class Syntax' at ( https://www.tutorialspoint.com/how-to-create-a-private-variable-in-javascript ).

* Square brackets for #Type = ['ADULT', 'CHILD', 'INFANT'];
See top answer at ( https://stackoverflow.com/questions/1629539/what-do-square-brackets-around-an-expression-mean-e-g-var-x-a-b ).



The penny has finally dropped! I understand how a "demonstration" can be given in a CV. I would need to develop my current CV to provide engineering specifications and implementations.

1. Convert my Java cinema-tickets app to JavaScript.

2. Develop my Java cinema-tickets app. This would improve the app.

3. Continue to develop my CV to v2.0, and submit an application for the Senior Software Engineer position.

4. Write to Sam Hackett. Ask him for instructions on which option to pursue.




















