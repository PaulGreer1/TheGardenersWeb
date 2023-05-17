### Statement of data requirements

Requirements in bold text. Analysis in light text. We go through the requirements, sentence by sentence. We look for entity types, attributes and entity relationships. The result will be a database schema, including a conceptual data model (CDM) with an entity relationship diagram (ERD) (see TGW_CDM_n.png).<br><br>
Format:<br>
#### A sentence from the requirements
Noun or noun phrase | Justification for retention or rejection.

<hr>

#### A garden object may be any object in the garden that we want to record data about.

garden object | Entity type: GardenObject<br>
object | Same as garden object.<br>
garden | Entity type: Garden<br>
data | NA

#### Trees, shrubs, flowers, rocks, ornaments, benches, etc. are all garden objects.

Trees | Tree is a value of the Name attribute of the ExtraAttribute entity type.<br>
shrubs | Shrub is a value of the Name attribute of the ExtraAttribute entity type.<br>
flowers | Flower is a value of the Name attribute of the ExtraAttribute entity type.<br>
rocks | Rock is a value of the Name attribute of the ExtraAttribute entity type.<br>
ornaments | Ornament is a value of the Name attribute of the ExtraAttribute entity type.<br>
benches | Benches is a value of the Name attribute of the ExtraAttribute entity type.


#### Some plants such as trees, shrubs, etc., may be typed by their seasonal foliage; evergreen, semi-evergreen, deciduous, etc., and may have a seasonal lifecycle type; annual, biennial, perennial, etc..

Plants | Same as garden object.<br>
seasonal foliage | SeasonalFoliage is a value of the Name attribute of the ExtraAttribute entity type.<br>
evergreen | Evergreen is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Evergreen'<br>
semi-evergreen | SemiEvergreen is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'SemiEvergreen'<br>
deciduous | Deciduous is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Deciduous'<br>
seasonal lifecycle type | SeasonalLifecycle is a value of the Name attribute of the ExtraAttribute entity type.<br>
annual | Annual is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Annual'<br>
biennial | Biennial is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Biennial'<br>
perennial | Perennial is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Perennial'

#### A garden object may be one of many entity types.

entity types | NA


#### For example, a garden may contain different garden objects such as shrubs and statues.

statues | Statue is a value of the Value attribute of the ExtraAttribute entity type. ExtraAttribute.Value = 'Statue'


#### These two entity types will probably have some attributes which are mutually exclusive, while they may have others in common.

attributes | Same as standard attributes.

#### If we were to define all possible garden objects under an entity type called GardenObject, then there would be too many redundant attributes because many garden objects have attributes which other garden objects don't have.


#### Therefore, there would be too many null values in the GardenObject table.


#### On the other hand, if we were to define an entity type for each of these objects, then we would have too many entity types, and hence too many tables in the database.


#### We solve this problem by defining an entity type called ExtraAttribute. The ExtraAttribute entity type holds attributes which are not common to all garden objects. An ExtraAttribute has a name and a value.

ExtraAttribute | Entity type: ExtraAttribute<br>
name | Attribute of ExtraAttribute: Name<br>
value | Attribute of ExtraAttribute: Value

#### A garden object is identified by a numeric identifier which is unique within the garden area that contains it, along with the containing garden area's identifier (which is posted as a foreign key in GardenObject). All garden objects have a name (e.g. Blonde Beauty), a type (shrub, flower, ornament, etc.), a description, a monetary value, .. .

unique numeric identifier | Attribute of GardenObject: Id<br />
a name | Standard attribute of GardenObject: Name<br>
Blonde Beauty | A value of the name standard attribute of the GardenObject entity type.<br>
a type | Standard attribute of GardenObject: Type<br>
a description | Standard attribute of GardenObject: Description<br>
a monetary value | Standard attribute GardenObject: MonetaryValue

#### Some garden objects have attributes which other garden objects don't have.

#### For example, a shrub may have a Latin name as well as a name, whereas a statue will have a name, but not a Latin name.

Latin name | Value of the Name attribute of the ExtraAttribute entity type: LatinName

#### So, as well as the standard attributes common to all garden objects, each garden object has zero or more extra attributes, and each extra attribute belongs to zero or more garden objects.

standard attributes | Attributes which are common to all garden objects.<br>
extra attributes | Attributes which a garden object may have in addition to the standard attributes of garden objects in general.<br>

[GardenObject] >o----------------------o< [ExtraAttribute]

[GardenObject] -o----------m< [GardenObjectExtraAttribute] >m----------o- [ExtraAttribute]

#### An extra attribute has a name and a value.

name | Attribute of the ExtraAttribute entity type: Name<br>
value | Attribute of the ExtraAttribute entity type: Value

#### A garden is divided into garden areas. Each garden contains one or more garden areas, and each garden area is contained by just one garden.

garden area | Entity type: GardenArea

#### Each garden area contains zero or more garden objects, and each garden object is contained by just one garden area. A garden area is identified by a numeric identifier which is unique within the garden that contains it, along with the containing garden's identifier (which is posted as a foreign key in GardenArea).

[Garden] -m----------------------m< [GardenArea] -o----------------------m< [GardenObject]

#### A garden, garden area, garden object, etc., may be depicted by zero or more images, and each image depicts zero or one garden, garden area, garden object, etc..

images | Entity type: Image

#### Each image has a unique identifier, a name and a directory path to its storage location on disk.

unique identifier | Attribute of the Image entity type: Id<br>
name | Attribute of the Image entity type: Name<br>
directory path | Attribute of the Image entity type: DirectoryPath<br>
storage location | Same as directory path.<br>
disk | NA

#### Each gardener cares for zero or more gardens, and each garden is cared for by zero or more gardeners.

gardener | Entity type: Gardener<br>

[Gardener] >o----------------------o< [Garden]

[Gardener] -o----------m< [GardenerGarden] >m----------o- [Garden]

#### A gardener has a unique identifier, a name, an email address.

unique identifier | Attribute of the Gardener entity type: Id<br>
name | Attribute of the Gardener entity type: Name<br>
email address | Attribute of the Gardener entity type: EmailAddress

#### Each garden object is owned by zero or one gardener, and each gardener owns zero or more garden objects.

[GardenObject] >o----------------------o- [Gardener]

#### So a gardener may be assigned to a garden area without owning any garden objects in that garden area, and may own garden objects in a garden area without being assigned to that area.<br>

#### Each gardener is assigned to zero or more garden areas, and each garden area has zero or more gardeners assigned to it.

[Gardener]  >o----------------------o< [GardenArea]

[Gardener] -o----------m< [GardenerGardenArea] >m-----------o- [GardenArea]

#### Each garden task is led by just one gardener, and each gardener may lead on zero or more garden tasks.

task | Entity type: Task<br>

[Task] >m----------------------o- [Gardener]


#### A task has a numeric id, a name, a description, a start date, a deadline date, .. .

numeric id | Attribute of the Task entity type: Id<br />
name | Attribute of the Task entity type: Name<br>
description | Attribute of the Task entity type: Description<br>
start date | Attribute of the Task entity type: StartDate<br>
deadline date | Attribute of the Task entity type: DeadlineDate
