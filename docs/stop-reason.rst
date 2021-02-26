.. _rststopreason:

===========
Stop Reason
===========

This element represents the reason for which a medication was discontinued.
A similar field in `OMOP <https://github.com/OHDSI/CommonDataModel/wiki/DRUG_EXPOSURE>`_
is represented as a varchar(20), with reasons including regimen completed, changed, removed,




.. code-block:: json

    enum StopReason {
        UNKNOWN_STOP_REASON = 0;
        REGIMEN_COMPLETED = 1;
        REGIMEN_CHANGED = 2;
        REMOVED = 3; // what does 'removed' mean here?
    }


To be discussed is whether this can be represented by Ontology terms.