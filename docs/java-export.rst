.. _rstjavaexport:

####################################
Exporting and Importing Phenopackets
####################################

It is easy to export Phenopackets in JSON, YAML, or protobuf format. Bear in mind that protobuf was designed as a
wire-format allowing for 'schema evolution' so this is safest to use in this environment. It would be advisable to store
your data in a datastore with a schema relevant to your requirements and be able to map that to the relevant Phenopacket
message types for exchange with your users/partners. If you don't it is possible that breaking changes to the schema will
mean you cannot exchange data with parties using a later version of the schema or if you update the schema your tools are
using they will no longer be able to read your data written using the previous version. While protobuf allows for
'schema evolution' by design which will limit the impact of changes to the schema precipitating this scenario, it is
nonetheless a possibility which the paranoid might wish to entertain.

JSON export
~~~~~~~~~~~
In many situations it
may be desirable to export the Phenopacket as `JSON <https://en.wikipedia.org/wiki/JSON>`_. This is easy with
the following commands (we show how to create a Phenopacket in Java elsewhere).

.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import com.google.protobuf.util.JsonFormat;
    import java.io.IOException;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        String jsonString = JsonFormat.printer().includingDefaultValueFields().print(pp);
        System.out.println(jsonString);
     } catch (IOException e) {
       e.printStackTrace();
     }



YAML export
~~~~~~~~~~~
`YAML <https://yaml.org/>`_ (YAML Ain't Markup Language) is a human friendly data serialization
  standard for all programming languages.

.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import com.google.protobuf.util.JsonFormat;
    import java.io.IOException;
    import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        String jsonString = JsonFormat.printer().includingDefaultValueFields().print(phenoPacket);
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        String yamlString = new YAMLMapper().writeValueAsString(jsonNodeTree);
        System.out.println(yamlString);
    } catch (IOException e) {
        e.printStackTrace(); // or handle the Exception as appropriate
    }


Protobuf export
~~~~~~~~~~~~~~~
For most use case, we recommend using JSON as the serialization format for Phenopackets. Protobuf
is more space efficient than JSON but it is a binary format that is not human readable.


.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import java.io.IOException;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        phenoPacket.writeTo(System.out);
     } catch (IOException e) {
        e.printStackTrace(); // or handle the Exception as appropriate
    }

We can write to any OutputStream (replace System.out in the above code), e.g. a file or network.


Importing Phenopackets (JSON format)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
There are multiple ways of doing this with different JSON libraries e.g. Jackson, Gson, JSON.simple.... The following
code explains how to convert the JSON String object into a protobuf class. This isn't limited to a Phenopacket message,
so long as you know the type of message contained in the json, you can merge it into the correct Java representation.

.. code-block:: java

    String phenopacketJsonString = // Phenopacket in JSON as a String;
    try {
        Phenopacket.Builder phenoPacketBuilder = Phenopacket.newBuilder();
        JsonFormat.parser().merge(jsonString, phenoPacketBuilder);
        Phenopacket phenopacket = phenoPacketBuilder.build();
        // do something with phenopacket ...
    } catch (IOException e1) {
        e1.printStackTrace(); // or handle the Exception as appropriate
    }