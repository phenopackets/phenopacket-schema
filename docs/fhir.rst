.. _rstfhir:

=========================
FHIR Implementation Guide
=========================

Phenopackets on FHIR is a FHIR implementation guide based on the Phenopackets standard. It is meant to be conceptually
equivalent but not directly interoperable. The guide is hosted at:

http://phenopackets.org/core-ig/

Briefly, the phenopacket equivalents to various FHIR resources are as follows:

:ref:`rstage` is mapped to the FHIR using `Unified Code for Units of Measure (UCUM) <http://unitsofmeasure.org/trac/>`_.
See also `Condition onset <http://build.fhir.org/condition-definitions.html#Condition.onset_x_>`_.

:ref:`rstbiosample` maps to `Specimen <http://www.hl7.org/fhir/specimen.html>`_.

:ref:`rstevidence` maps to `Condition.evidence <https://www.hl7.org/fhir/condition-definitions.html#Condition.evidence>`_.

:ref:`rstexternalreference` maps to `Reference <https://www.hl7.org/fhir/references.html>`_.

:ref:`rstindividual` maps to `Patient <https://www.hl7.org/fhir/patient.html>`_.

:ref:`rstontologyclass` maps to `CodeableConcept <http://www.hl7.org/fhir/datatypes.html#CodeableConcept>`_.
See also `Coding <http://www.hl7.org/fhir/datatypes.html#Coding>`_.

:ref:`rstphenotypicfeature` maps to `Condition <https://www.hl7.org/fhir/condition.html>`_ or
`Observation <https://www.hl7.org/fhir/observation.html>`_ elements. The FHIR mapping of
the type element of PhenotypicFeature is *Condition.identifier*, the mapping of the severity element
is *Condition.severity*, the mapping of onset is *Condition.onset*.

:ref:`rstprocedure` maps to `Procedure <https://www.hl7.org/fhir/procedure.html>`_.

:ref:`rstsex` maps to `AdministrativeGender <https://www.hl7.org/fhir/codesystem-administrative-gender.html>`_.

:ref:`rstresource` maps to `CodeSystem <http://www.hl7.org/fhir/codesystem.html>`_.
