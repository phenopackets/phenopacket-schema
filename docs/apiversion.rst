.. _rstapiversion:

===========
API Version
===========

This field will be used to indicate the version of Phenopackets used. Software will be provided to update version 1.0
phenopackets to version 2.0 once the new version has been approved by GA4GH.



.. code-block:: json

    enum ApiVersion {
        v1_0 = 0;
        v1_1 = 1;
        v2_0 = 2;
    }
