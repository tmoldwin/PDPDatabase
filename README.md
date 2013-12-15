PDPDatabase
===========

This is a database system loosely based on the parallel distributed processing theory of memory proposed by Jay McClelland in his conference paper, Proceedings of the third annual conference of the Cognitive Science Society.
Berkeley, California , August 19-21 , 1981. 

The general idea of this memory storage system is that each data item (row in a database) is stored as a set of connections between nodes, where nodes are discrete properties of an item. For example, in McClelland's "Jets and Sharks" example, each row of the database is a person who belongs to either the Jets team or the Sharks gang. Each person has a marital status, age, and profession. Each possible team, marital status, age, and profession is a node (e.g. "20's" and "30's" are separate nodes, as are "Jets" and "Sharks"). Each node belongs to a "cluster" representing the type of data that node represents ("Jets" and "Sharks" both belong to the "gang" cluster). A "row" in the database is thus a set of connections between nodes in each cluster.

This database format allows one to make direct queries (binary query) to find exact matches to a query. One can query ["sing.", "Jets"] to find all single members of the Jets gang. One can also use the database to make probabilistic inferences queries using a naive Bayes classifier. For example, if one wishes to find out whether a married male in his 40's is more likely to be a Jet or a Shark, she may perform the query ("Gang", ["40's", "married", "male") to see find the maximum likelihood estimate for each gang.
