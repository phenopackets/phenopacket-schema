Matchmaker Exchange API Example
=

The purpose of this package is a demonstration of how the phenopackets core schema can be used to compose other APIs.

The benefit of using common base building blocks is that it becomes much simpler to exchange data between different groups. For example, assuming both [Beacon](https://beacon-network.org) and [Matchmaker Exchange](http://www.matchmakerexchange.org/) both decide to adopt the phenpackets core a client would be able to create common ```org.phenopackets.api.v1.core.Variant``` messages and use these directly to [query a Beacon](https://beacon-network.org/#/developers/api/beacon-network#bn-response) and add more information to also [search the MME](https://github.com/ga4gh/mme-apis/blob/master/search-api.md#search-request).

_n.b._ the current ```org.phenopackets.api.v1.core.Variant``` and ```org.phenopackets.api.v1.core.VariantAnnotation``` messages are placeholders for the outcome of the relevant GA4GH working groups.