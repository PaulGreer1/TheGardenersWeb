## TheGardenersWeb Database

### Entity relationship diagram

<TGW_ERD.png>

### Entity type headings

Garden ( GardenId (Integer), GardenName (String), Description (String) )

GardenGardener ( GardenId (Integer) (refs Garden.GardenId), GardenerId (Integer) (refs Gardener.GardenerId) )

Gardener ( GardenerId (Integer), GardenerName (String), EmailAddress (String) )

GardenAreaGardener ( GardenAreaId (Integer) (refs GardenArea.GardenAreaId), GardenerId (refs Gardener.GardenerId) (Integer) )

GardenArea ( GardenAreaId (Integer), GardenAreaName (String), GardenId (Integer) (refs Garden.GardenId)

GardenObject ( GardenObjectId (Integer), GardenObjectName (String), Type (String), Description (String), MonetaryValue (Decimal(2dp)), GardenAreaId (Integer) (refs GardenArea.GardenAreaId (the 'location' of the object) ), GardenerId (Integer) (refs Garderner.GardernerId (the owner of the object ) )

GardenObjectExtraAttribute( GardenObjectId (Integer) (refs GardenObject.GardenObjectId), ExtraAttributeId (Integer) (refs ExtraAttribute.ExtraAttributeId )

ExtraAttribute ( ExtraAttributeId (Integer), ExtraAttributeName (String), DataType (String), Value (String) )

Task( TaskId (Integer), TaskName (String), Description (String), StartDate (String), DeadlineDate (String), GardenerId (Integer) (refs Gardener.GardenerId) )

Image( ImageId (Integer), ImageName (String), ImageFilename (String) )


The following is the ImageEntityType relation that we'll use in the database schema:

ImageEntityType( ImageId (Integer) (refs Image.ImageId), EntityTypeName (String), EntityId (Integer) )

The following is not standard SQL, but is how the relation would look if it were possible:

ImageEntityType( ImageId (Integer) (refs Image.ImageId), EntityTypeName (String), EntityId (Integer) (refs EntityTypeName.EntityId) )

### Order of table creation

Garden, GardenArea, Gardener, Task, GardenGardener, GardenAreaGardener, GardenObject, ExtraAttribute, GardenObjectExtraAttribute, Image, ImageEntityType

### The ImageEntityType entity type

<pre>
ImageEntityType

ImageId | EntityId | EntityTypeName
--------+----------+---------------
6       | 1        | Garden
2       | 1        | Garden
7       | 2        | Garden
4       | 1        | GardenArea
10      | 2        | GardenArea
1       | 2        | GardenArea
3       | 2        | GardenArea
8       | 3        | GardenArea
9       | 4        | GardenArea
5       | 1        | GardenObject
11      | 1        | GardenObject
12      | 2        | GardenObject

Image ( ImageId, ImageName, ImageFilename )

1 | iname1 | ifilename1
2 | iname2 | ifilename2
...
</pre>

It is possible to use entity type names as database table attributes, and use them in SQL queries because SQL queries are strings which are formed in apps and then passed to the DBMS. So, if we want images for, say, a particular garden area with GardenArea.GardenAreaId = 2 (i.e. ImageEntityType.EntityId = 2), then, since we know that the table will be called GardenArea and the row identifier in the GardenArea table (and the ImageEntityType table) is 2, then images with EntityIds 10, 1 and 3 would be returned.
