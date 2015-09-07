/** Class representing a cluster of Page Docs */
public class Cluster implements Comparable<Cluster> {
	private Vector centroid;
	private final PageDocList pageDocs = new PageDocList();
	private final int numFeatures;

	/** Construct a cluster with a single member document. */
	public Cluster(PageDoc pageDoc) {
		add(pageDoc);
		centroid = new Vector(pageDoc.getVector());
		numFeatures = pageDoc.getNumFeatures();
	}

	/** Add document to cluster and mark document as allocated. */
	public void add(PageDoc pageDoc) {
		pageDoc.setIsAllocated();
		pageDocs.add(pageDoc);
	}

	/** Remove all documents from a cluster. */
	public void clear() {
		pageDocs.clearIsAllocated();
		pageDocs.clear();
	}

	@Override
	public int compareTo(Cluster cluster) {
		if (pageDocs.isEmpty() || cluster.pageDocs.isEmpty()) {
			return 0;
		}
		return pageDocs.get(0).compareTo(cluster.pageDocs.get(0));
	}

	/** Get centroid of cluster. */
	public Vector getCentroid() {
		return centroid;
	}

	/** Get pages in cluster. */
	public PageDocList getDocuments() {
		return pageDocs;
	}

	/** Get the number of pages in the cluster. */
	public int size() {
		return pageDocs.size();
	}

	/** Sort the documents within a cluster by ID. */
	public void sort() {
		pageDocs.sort();
	}

	@Override
	public String toString() {
		return pageDocs.toString();
	}

	/** Update centroids and centroidNorms for this cluster. */
	public void updateCentroid() {
		centroid = new Vector(numFeatures);
		for (PageDoc document : pageDocs) {
			centroid = centroid.add(document.getVector());
		}
		centroid = centroid.divide(size());
	}
}
