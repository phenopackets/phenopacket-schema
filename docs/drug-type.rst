.. _rstdrugtype:

=========
Drug Type
=========

Following the `OMOP definitions <https://github.com/OHDSI/CommonDataModel/wiki/DRUG_EXPOSURE>`_ of
these items, we consider that drugs include prescription and over-the-counter
medicines, vaccines, and large-molecule biologic therapies.

Drugs can be administered in different contexts, and this element captures information about this.




.. code-block:: json

    enum DrugType {
        UNKNOWN_DRUG_TYPE = 0;
        PRESCRIPTION = 1;
        EHR_MEDICATION_LIST = 2;
        ADMINISTRATION_RELATED_TO_PROCEDURE = 3;
    }


To be discussed is whether this can be implemented instead as an ontology term.
For instancec, NCIT has a term for `Prescription <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C28180>`_.

