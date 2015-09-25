# Webpage Correlation Engine

@author Mohit Mishra
Indian Institute of Technology (BHU) Varanasi
Language: Java

This project/software basically clusters out similar pages in one clusters. 

To do this, we use the concept of document clustering (easily available on the internet for understanding).
The question now is, given a bunch of URLs, how woul you classify which set of urls are similar/different to/from each other. For this, the problem becomes simple to understand if we can "transform" the URL  problem to a text document, and then apply document clustering. However, there is a major concern: 
1. Make sure relevant content is chosen for the document text. 
2. Noise removal

The algorithm used here, however, doesn't consider the noise at all. The noise is self-removed while performing document cosine similarity. Having mentioned this, cosine similarity metric is used to quantitatively determine the closeness between two text docs (and hence two webpages). 

Adaptive K-Means Clustering is used to classify the webpages. Adaptive in the sense that the algorithm/program itselfs computes the "optimized" value of k instead of statically defining it and experimenting over a range of values of k. This is based on the ratio of intra-cluster distance and inter-cluster distance. The threshold, in our case, worked out best at 0.5 (might be different from cases to cases). 


