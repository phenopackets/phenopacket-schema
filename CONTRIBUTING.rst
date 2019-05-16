------------
Contributing
------------

Non-technical folks would be best served by opening an issue on the `issue pages`_ - please check to see no one else has opened a similar/duplicated issue already.

.. _issue pages: https://github.com/phenopackets/phenopacket-schema/issues

Bear in mind that this is a standard used by many other resources, so it might not be suitable to add just one minor thing to serve your specific requirements. The schema is also developed using `semver`_ so minor and patch additions are far more likely to make their way into the standard than major breaking changes as we aim for stability and compatibility.

.. _semver: https://semver.org/

If you would like to contribute code changes to the specification please follow these steps:

Fork, then clone the repo:

.. code:: bash

    git clone git@github.com:your-username/phenopacket-schema.git
    
Set up your machine. This requires Java 1.8 or higher.

Make sure the tests pass:

.. code:: bash

    ./mvnw clean package

Make your change. Add tests for your change. Make the tests pass:

.. code:: bash

    ./mvnw clean package

Push to your fork and `submit a pull request`_.

.. _submit a pull request: https://github.com/phenopackets/phenopacket-schema/compare/

Wait for a response. We may suggest some changes or improvements or alternatives.

Some things that will increase the chance that your pull request is accepted:

* Write tests.
* Follow the current style - for protobuf see the `protobuf style guide`_.
* Write a `good commit message`_.

.. _protobuf style guide: https://developers.google.com/protocol-buffers/docs/style
.. _good commit message: http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
