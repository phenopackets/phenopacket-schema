.. _rstgenomincinterpretation:

#####################
GenomicInterpretation
#####################

This element is used as a component of the :ref:`rstinterpretation` element, and describes the
interpretation for an individual variant or gene. Note that multiple variants or genes
may support the interpretation related to one disease. See the :ref:`rstinterpretation` element
for examples.


Data model
##########

.. list-table:: Definition  of the ``GenomicInterpretation`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - subject_or_biosample_id
     - string
     - required
     - The id of the patient or biosample that is the subject being interpreted
   * - status
     - enum Status
     - required
     - status of the interpretation
   * - call
     - oneof :ref:`rstgeneinterpretation` or :ref:`rstvariantinterpretation`
     - required
     - represents the interpretation



 // TODO: see issue #269 - DONE
    // identifier for the subject of the interpretation. This MUST be the individual id or a biosample id.
    string subject_or_biosample_id = 7;

    enum Status {
        UNKNOWN_STATUS = 0;
        REJECTED = 1;
        CANDIDATE = 2;
        CONTRIBUTORY = 3;
    }
    Status status = 1;

    // TODO: See issue #257 - DONE
    // Gene or variant identified in subject or biosample and interpreted in relation to the disease diagnosis.
    oneof call {
        GeneInterpretation gene = 2;
        VariantInterpretation variant = 3;
    }

Example
#######


.. code-block:: yaml

  genomicInterpretation:
    subjectOrBiosampleId: "subject 1"
    status: "CONTRIBUTORY"
    variant:
        variantFinding: "PATHOGENIC"
        variant:
            hgvsAllele:
                hgvs: "NM_001848.2:c.877G>A"
            zygosity:
                id: "GENO:0000135"
            label: "heterozygous"



Explanations
############

subject_or_biosample_id
~~~~~~~~~~~~~~~~~~~~~~~

Each genomic interpretation is based on a genomic finding in the germline DNA of the :ref:`rstindividual`
referenced in the phenopacket or of a :ref:`rstbiosample` derived from the individual.
The id used here must therefore match with the Individual.id or with the Biosample.id element.

status
~~~~~~

This is an enumeration that describes the conclusion made about the genomic interpretation.

- UNKNOWN_STATUS: unknown
- REJECTED: the variant or gene reported here is interpreted *not* to be related to the diagnosis
- CANDIDATE: the variant or gene reported here is interpreted to *possibly* be related to the diagnosis
- CONTRIBUTORY: the variant or gene reported here is interpreted to be related to the diagnosis

There are several situations in which one should use ``CONTRIBUTORY``. In an autosomal dominant
Mendelian disease, one variant is causative. In this case, one would classify it as ``CONTRIBUTORY``
and the :ref:`rstinterpretation` object that contains the genomic interpretation would use
``SOLVED``. In the case of an autosomal recessive disease, one ``CONTRIBUTORY`` genomic interpretation
would be used for a homozygous causative variant, and two would be used for compound heterozygous variants.
In cancer, ``CONTRIBUTORY`` can be used for multiple variants, and the corresponding
:ref:`rstinterpretation` object could classify them as ``ACTIONABLE``, for instance, if a targeted treatment is available for the variant.

call
~~~~

TODO