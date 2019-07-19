.. _rstage:

===
Age
===


The Age element allows the age of the subject to be encoded in several different ways that support different use cases.
Age can be encoded either as `ISO8601 duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_ or as time
interval (preferred). Alternatively, the age can be encoded using an ontology term.


**Data model**

.. csv-table::
   :header: Field, Type, Status, Description
   :align: left

   age, string, optional, An ISO8601 string represent age
   age_class, :ref:`rstontologyclass`, optional, an ontology class representing age



If the Age message is used, at least one of the two elements ``age`` and ``age_class`` must be present.


**Example**

.. parsed-literal::

  {
      "age": "P25Y3M2D",
  }




The string element (string age=1) should be used for ISO8601 duration or time intervals.
The use of time intervals makes an additional anchor unnecessary (i.e. DOB and age can be
represented as start-anchored time interval, e.g. 1967-11-21/P40Y10M05D). For many use cases,
less precise strings will be preferred for privacy reasons, e.g., P40Y.

Alternatively, an :ref:`ontology class <rstontologyclass>` can be used to represent age. We recommend the
`Human Developmental Stages ontology <https://www.ebi.ac.uk/ols/ontologies/hsapdv>`_.
For example `second decade human stage <https://www.ebi.ac.uk/ols/ontologies/hsapdv/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FHsapDv_0000236>`_
or `114-year-old human stage <https://www.ebi.ac.uk/ols/ontologies/hsapdv/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FHsapDv_0000255>`_.



age
~~~

It is possible to represent age using a string that should be formated according  as ISO8601
duration or time intervals. We recommend using the ISO 8601 standard for representing
age as a `Duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_.

age_class
~~~~~~~~~

Alternatively, the age can be represented using an :ref:`ontology class <rstontologyclass>`. For humans,
we recommend using the `Human Developmental Stages <https://www.ebi.ac.uk/ols/ontologies/hsapdv>`_ ontology.
There are several reasons why users might choose to use an :ref:`ontology class <rstontologyclass>` rather than an ISO8601 string
to represent age, including the need to protect privacy by indicating age in an imprecise way, for instance,
with the Human Developmental Stages ontology term second decade human stage
(`HSAPDV:0000236 <https://www.ebi.ac.uk/ols/ontologies/hsapdv/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FHsapDv_0000236>`_).




FHIR mapping
~~~~~~~~~~~~
This element is mapped to the FHIR using `Unified Code for Units of Measure (UCUM) <http://unitsofmeasure.org/trac/>`_.
See also `Condition onset <http://build.fhir.org/datatypes and http://build.fhir.org/condition-definitions.html#Condition.onset_x_>`_.



.. _rstagerange:

========
AgeRange
========

The AgeRange element is inteded to be used when the age of a subject is represented by a bin, e.g., 5-10 years. Bins
such as this are used in some situations in order to protect the privacy of study participants, whose age is then
represented by bins suich as 45-49 years, 50-54 years, 55-59 years, and so on.





**Data model**

.. csv-table::
   :header: Field, Type, Status, Description
   :align: left

   start, :ref:`rstage`, required, An Age message
   end, :ref:`rstage`, required, An Age message


|
|
|
|


**Example**

For instance, to represent the bin 45-49 years, one could use an Age element with **P45Y** as the start element of the AgeRange elemnt,
and an Age element with **P49Y** as the end element.




.. parsed-literal::

  {
    "start": {
        "age": "P45Y"
    },
    "end": {
        "age": "P49Y"
    }
  }




