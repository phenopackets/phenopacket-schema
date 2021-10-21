.. _rstradiotherapy:

################
RadiationTherapy
################

Radiation therapy (or radiotherapy) uses ionizing radiation, generally as part of cancer treatment to control
or kill malignant cells.



Data model
##########


.. list-table:: Definition  of the ``Radiotherapy`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - modality
     - :ref:`rstontologyclass`
     - 1..1
     - The modality of radiation therapy (e.g., electron, photon,...). REQUIRED.
   * - body_site
     - :ref:`rstontologyclass`
     - 1..1
     - The anatomical site where radiation therapy was administered. REQUIRED.
   * - dosage
     - int32
     - 1..1
     - the total dose given in units of Gray (Gy). REQUIRED.
   * - fractions
     - int32
     - 1..1
     - The total number of fractions delivered as part of treatment. REQUIRED.


Explanations
############

modality
~~~~~~~~

Terms from the NCIT can be used to represent the modality of radiation therapy. Four examples are shown here:

* `Electron Beam (NCIT:C28039) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C28039&viewMode=All&siblings=false>`_
* `Proton Radiation (NCIT:C40431) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C40431&viewMode=All&siblings=false>`_.
* `Photon Beam Radiation Therapy (NCIT:C104914) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C104914>`_
* `High-LET Heavy Ion Therapy (NCIT:C15458) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C15458>`_.

The NCIT terms themselves specify whether the radiation therapy was administered externally or internally. For
instance, NCIT terms from the `Internal Radiation Therapy (NCIT:C15195) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C15195&viewMode=All&siblings=false>`_
subhierarchy would be used to indicate a type of internal radiation therapy.

body_site
~~~~~~~~~
The anatomical site that was irradiated. An `uberon <https://www.ebi.ac.uk/ols/ontologies/uberon>`_ term can be used.

dosage
~~~~~~
The total dosage administered, indicated in units of Gray.

fractions
~~~~~~~~~
The number of fractions into which the radiation dosage was divided.





