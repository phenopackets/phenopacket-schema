===================
Geolocation element
===================

A GeoLocation object provides information about a geographic position
related to a record. Note that the GeoLocation element can be used within
a :ref:`Individual element` to denote a location related to a person, but
it can also be used within other elements such as *Experiment*. In the intended use cases,
the elements that are relevant to the encasing element should be chosen, and software
should perform context-dependent Q/C as necessary.


Examples could be:

- an address, e.g. of a lab performing an analysis
- provenance of an individual, obfuscated to a larger order administrative entity (Suffolk, U.K.)
- a lat/long/alt position where an environmental sample was collected

The geographic point object uses the default units from the `DCMI point scheme <http://dublincore.org/documents/dcmi-point/>`_
and avoids optional representation in non-standard units.

This is the protobuf representation of a GeoLocation::

  message GeoLocation {  
    string label = 1;
    string precision = 2;
    double latitude = 3;
    double longitude = 4;
    double altitude = 5;
  }

label of the GeoLocation
========================
The label is a text representation, preferably using standard geographic identification
elements, of the corresponding latitude,longitude(,altitude)
This representation serves the purposes to

- capture standard data entry parameters
- provide a sanity check for latitude,longitude values

For example:

- 34 Washington Blvd, Marina del Rey, CA  90292, United States
- Str Marasesti 5, 300077 Timisoara, Romania
- Heidelberg, Deutschland

precision
=========
This element is an optional indication of the maximum precision to be derived from the
latitude and longitude values. For example,  Given a street address such as
"Winterthurerstrasse 190, 8057 Zürich, Switzerland", a privacy driven (destructive) obfuscation
approach could recode this to "latitude": 47.37, "longitude": 8.54, while providing
"precision":"city", "label": "Zürich, Switzerland", indicating that the original location could correspond to any
 latitude,longitude point value inside the administrative boundaries of
 the city of Zürich, Switzerland. The implementation and interpretation of this field is
 application-dependent.

latitude
========
This element represents signed decimal degrees (North, relative to Equator).

longitude
=========
This element represents signed decimal degrees (East, relative to the `IERS Reference Meridian <https://en.wikipedia.org/wiki/IERS_Reference_Meridian>`_).

altitude
========
This is an optional element that may be used in case an environmental sample was collected above the surface of the earth, e.g., for environmental analysis.
