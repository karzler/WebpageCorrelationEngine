
/**
 * Cluster Engine interface. Groups documents into Clusters based on 
 * similarity of their content (cosine similarity).
 */
public interface ClusterEngine {
	/** Cluster the provided list of documents. */
	public ClusterList cluster(PageDocList documentList);
}
