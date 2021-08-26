.. _rstjavaevidence:

###############
Evidence (Java)
###############

The evidence code is used to document the support for an assertion. Here, we will show an example
for the assertion that flexion contractures are found in `stiff skin syndrome <https://omim.org/entry/184900>`_.



.. code-block:: java

    import org.phenopackets.schema.v1.core.Evidence;
    import org.phenopackets.schema.v1.core.ExternalReference;
    import org.phenopackets.schema.v1.core.OntologyClass;

    OntologyClass publishedClinicalStudy = OntologyClass.
                newBuilder().
                setId("ECO:0006017").
                setLabel("author statement from published clinical study used in manual assertion").
                build();
        ExternalReference reference = ExternalReference.newBuilder().
                setId("PMID:20375004").
                setDescription("Mutations in fibrillin-1 cause congenital scleroderma: stiff skin syndrome").
                build();
        Evidence evidence = Evidence.newBuilder().
                setEvidenceCode(publishedClinicalStudy).
                setReference(reference).
                build();


This code produces the following Evidence element.


.. code-block:: json

    {
        evidence_code {
            id: "ECO:0006017"
            label: "author statement from published clinical study used in manual assertion"
        }
        reference {
            id: "PMID:20375004"
            description: "Mutations in fibrillin-1 cause congenital scleroderma: stiff skin syndrome"
        }
    }