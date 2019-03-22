===========
Age element
===========


The Age element allows the age of the subject to be encoded in several different ways that support different use cases.
Age can be encoded either as `ISO8601 duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_ or as time interval '(preferred). Alternatively, the age can be encoded using an ontology term. Here is the message::

  message Age {

    // 
    string age = 1;

    // An age class, e.g. corresponding to the use of "age of onset" in HPO.
    // HPO is recommended, for example, subclasses of "Onset":
    // http://purl.obolibrary.org/obo/HP_0003674
    // Example:
    //  age_class : { term_id : "HP:0003596", term : "Middle age onset" }
    OntologyClass age_class = 2;
  }

age (string)
============
It is possible to represent age using a string that should be formated according  as ISO8601
 duration or time intervals. The use of time intervals makes an additional
anchor unnecessary (i.e. DOB and age can be represented as start-anchored
time interval, e.g. P40Y10M05D). We recommend using the ISO 8601 standard for representing
age as a `Duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_.

age_class
=========
Alternatively, the age can be represented using an ontology class. (TODO)


FHIR mapping (Age)
==================
This element is mapped to the FHIR using  a `UCUM  measurement <http://unitsofmeasure.org/trac/>`_.



========
AgeRange
========
The AgeRange element is inteded to be used when the age of a subject is represented by a bin, e.g., 5-10 years. Bins
such as this are used in some situations in order to protect the privacy of study participants, whose age is then
represented by bins suich as 45-49 years, 50-54 years, 55-59 years, and so on. ::

  message AgeRange {
    Age start = 1;
    Age end = 2;
  }

For instance, to represent the bin 45-49 years, one could use an Age element with age=P45Y for the start element of the AgeRange elemnt,
and an Age element with age=P49Y for the end element.