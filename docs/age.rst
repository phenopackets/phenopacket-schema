.. _rstage:

###
Age
###


The Age element allows the age of the subject to be encoded in several different ways that support different use cases.
Age is encoded as `ISO8601 duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_.


Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   iso8601duration, string, 1..1, An ISO8601 string represent age


If the Age message is used, the ``iso8601duration`` value must be present.


Example
#######

.. code-block:: yaml

  age:
    iso8601duration: "P25Y3M2D"


The string element (string age=1) should be used for ISO8601 durations (e.g., P40Y10M05D). For many use cases,
less precise strings will be preferred for privacy reasons, e.g., P40Y.

age
~~~

It is possible to represent age using a string that should be formatted according to ISO8601
`Duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_.


.. _rstagerange:

########
AgeRange
########

The AgeRange element is intended to be used when the age of a subject is represented by a bin, e.g., 5-10 years. Bins
such as this are used in some situations in order to protect the privacy of study participants, whose age is then
represented by bins such as 45-49 years, 50-54 years, 55-59 years, and so on.


Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   start, :ref:`rstage`, 1..1, An Age message
   end, :ref:`rstage`, 1..1, An Age message


Example
#######

For instance, to represent the bin 45-49 years, one could use an Age element with **P45Y** as the start element of the AgeRange element,
and an Age element with **P49Y** as the end element. An AgeRange.end SHALL occur after AgeRange.start.

.. code-block:: yaml

  ageRange:
    start:
        iso8601duration: "P45Y"
    end:
        iso8601duration: "P49Y"




