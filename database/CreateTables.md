#### Relation headings

Garden ( GardenId (Integer), GardenName (String), Description (String) )

#### Sample database

Copy the following statements and paste them into your mysql command line:

<pre>

CREATE DATABASE TheGardenersWeb;
USE TheGardenersWeb;
CREATE TABLE garden (
    garden_id INT(8) UNSIGNED ZEROFILL NOT NULL,
    garden_name VARCHAR(30) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY( garden_id )
) ENGINE=InnoDB;
INSERT INTO garden VALUES
( 1, 'Alice\'s Garden', 'The perfect place for a very happy unbirthday!' ),
( 4, 'Bill\'s Garden', 'A garden for lazy lizards.' ),
( 5, 'Carroll\'s Garden', 'Free Cheshire Cat fertilizer available!' ),
( 6, 'Dodo\'s Garden', 'All aboard for fun on the lake.' );

</pre>
