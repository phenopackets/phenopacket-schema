.. _rstrequirements:

==================
Requirement Levels
==================


In protobuf3, all elements are optional, and so there is no mechanism within protobuf to declare that a certain field
is required. The Phenopacket schema does require some fields to be present and in some cases additionally requires that
these fields have a certain format (syntax) or intended meaning (semantics). Software that uses Phenopackets should
check the validity of the data with other means. We provide a Java implementation called
`Phenopacket Validator <https://github.com/phenopackets/phenopacket-validator>`_ that tests Phenopackets (and related
messages including Family, Cohort, and Biosample messages) for validity. Application code may additional check for
application-specific criteria.


The Phenopacket schema uses three requirement levels. The required/recommended/optional designations are
phenopacket-specific extensions used in the schema only (not code) and are not supported by protobuf.



Required
========
If a field is required, its presence is  an absolute requirement of the specification, failing which the entire
phenopacket is regarded as malformed. This corresponds to the key woirds ``MUST``, ``REQUIRED``, and ``SHALL`` in
`RFC2119 <https://www.ietf.org/rfc/rfc2119.txt>`_.

Validation software must emit an error if a required field is missing.

Recommended
===========

A field is not absolutely required, or there are valid reasons in particular circumstances that the field does
not apply to the intended use case of the Phenopacket. This corresponds to the key woirds ``SHOULD`` and ``RECOMMENDED`` in
`RFC2119 <https://www.ietf.org/rfc/rfc2119.txt>`_. For example, a variant may be associated with an id that can
be useful to have but is not necessary for an analysis. The variant NM_000138.4:c.*2024A>G is associated with the
id `rs558488257 <https://www.ncbi.nlm.nih.gov/snp/rs558488257>`_.

Validation software may emit a warning if a recommended field is missing.


Optional
========

A field is truly optional. This category can be applied to fields that are only useful for a certain type of data. For
instance, the ``background`` field of the ``variant`` message is only used for Phenopackets that describe animal
models of disease.

The general-purpose validator must not emit a warning about these fields whether or not they are present. It may be
appropriate for application-specific validators to emit a warning or even an error if a certain optional field is not
present.