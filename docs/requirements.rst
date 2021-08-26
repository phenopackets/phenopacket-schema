.. _rstrequirements:

##################
Requirement Levels
##################


The schema is formally defined using `protobuf3 <rstprotobuf>`_. In protobuf3, all elements are optional, and so there is no mechanism
within protobuf to declare that a certain field is required. The Phenopacket schema does require some fields to be
present and in some cases additionally requires that these fields have a certain format (syntax) or intended meaning
(semantics). Software that uses Phenopackets should check the validity of the data with other means. We provide a Java
implementation called `Phenopacket Validator <https://github.com/phenopackets/phenopacket-validator>`_ that tests
Phenopackets (and related messages including Family, Cohort, and Biosample messages) for validity. Application code may
additionally check for application-specific criteria.



Hierarchical requirements
#########################

The requirement levels that are shown for the various elements of the Phenopacket only apply if the element is used. For instance,
the :ref:`rstquantity` shows that the ``unit`` and ``value`` fields are required (the multiplicity is exactly 1 and the word REQUIRED is shown in the description).
In contrast the field ``reference_range`` is optional (the multiplicity may be 0 or 1 and neither REQUIRED nor RECOMMENDED is used
in the description). The requirements only apply if a :ref:`rstquantity` is used in a Phenopacket. For instance, Phenopackets that do
not contain :ref:`rstmeasurement` or :ref:`rsttreatment` elements do not contain :ref:`rstquantity` elements, and so the requirements for
the fields of :ref:`rstquantity` do not apply.


Multiplicity
############

The explanations for the various elements of the Phenopacket show the required multiplcities.

* ``0..1``: The element may be absent (0) or present (1), i.e., the element is optional. Elements with multiplicity ``0..1`` may be marked RECOMMENDED, otherwise they are OPTIONAL.
* ``1..1``: The element must be present (1), i.e., the element is REQUIRED
* ``0..*``: There may be from zero to an arbitrary number of elements, i.e., a potentially empty list
* ``1..*``: There may be from one to an arbitrary number of elements, i.e., a list that must not be empty



Levels
######

The Phenopacket schema uses three requirement levels. The required/recommended/optional designations are
phenopacket-specific extensions used in the schema only (not code) and are not supported by protobuf.



Required
========
If a field is required, its presence is  an absolute requirement of the specification, failing which the entire
phenopacket is regarded as malformed. This corresponds to the key words ``MUST``, ``REQUIRED``, and ``SHALL`` in
`RFC2119 <https://www.ietf.org/rfc/rfc2119.txt>`_.

Validation software must emit an error if a required field is missing. We note that natively protobuf messages never
return a null pointer, and so if a field is missing it will be an empty string, a zero, or default instance depending
on the datatype. Therefore, in practice, validation software does not need to check for null pointers.

Recommended
===========

A field is not absolutely required, or there are valid reasons in particular circumstances that the field does
not apply to the intended use case of the Phenopacket. This corresponds to the key words ``SHOULD`` and ``RECOMMENDED`` in
`RFC2119 <https://www.ietf.org/rfc/rfc2119.txt>`_. For example, a variant may be associated with an id that can
be useful to have but is not necessary for an analysis. The variant NM_000138.4:c.*2024A>G is associated with the
id `rs558488257 <https://www.ncbi.nlm.nih.gov/snp/rs558488257>`_.

Validation software may emit a warning if a recommended field is missing.


Optional
========

A field is truly optional. This category can be applied to fields that are only useful for a certain type of data. For
instance, the :ref:`rstbiosample` field of the :ref:`rstphenopacket` message is only used for Phenopackets that have an
associated biosample(s).

The general-purpose validator must not emit a warning about these fields whether or not they are present. It may be
appropriate for application-specific validators to emit a warning or even an error if a certain optional field is not
present.