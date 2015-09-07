
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/*
 * storing a list of clusters. 
 */
public class ClusterList implements Iterable<Cluster> {
	private final ArrayList<Cluster> clusters = new ArrayList<Cluster>();


	public void add(Cluster cluster) {
		clusters.add(cluster);
	}

	private double calcInterClusterDistance(DistanceMetric distance) {
		if (clusters.isEmpty()) {
			return 0;
		}
		double sumInterDist = 0;
		for (Cluster cluster1 : clusters) {
			for (Cluster cluster2 : clusters) {
				if (cluster1 != cluster2) {
					sumInterDist += distance.calcDistance(cluster1, cluster2);
				}
			}
		}
		// there are N * N-1 unique pairs of clusters
		return sumInterDist / (clusters.size() * (clusters.size() - 1));
	}


	private double calcIntraClusterDistance(DistanceMetric distance) {
		double sumIntraClusterDistance = 0;
		int numDocuments = 0;
		for (Cluster cluster : clusters) {
			double intraClusterDistance = 0;
			for (PageDoc document : cluster.getDocuments()) {
				intraClusterDistance += distance.calcDistance(document, cluster);
			}
			numDocuments += cluster.size();
			sumIntraClusterDistance += intraClusterDistance;
		}
		return sumIntraClusterDistance / numDocuments;
	}

	/**
	 * Calculate ratio of average intracluster distance to average intercluster distance. 
	 */
	public double calcIntraInterDistanceRatio(DistanceMetric distance) {
		if (clusters.isEmpty()) {
			return Double.MAX_VALUE;
		}
		double interClusterDistance = calcInterClusterDistance(distance);
		if (interClusterDistance > 0.0) {
			return calcIntraClusterDistance(distance) / interClusterDistance;
		} else {
			return Double.MAX_VALUE;
		}
	}

	/**
	 * Clear out documents from within each cluster. Used to cleanup after each clustering iteration.
	 */
	public void clear() {
		for (Cluster cluster : clusters) {
			cluster.clear();
		}
	}

	/**
	 * Find document with maximum distance to clusters in ClusterList. Distance to ClusterList is
	 * defined as the minimum of the distances to each constituent Cluster's centroid. This method is
	 * used during the cluster initialization in k-means clustering.
	 */
	public PageDoc findFurthestDocument(DistanceMetric distance, PageDocList documentList) {
		double furthestDistance = Double.MIN_VALUE;
		PageDoc furthestDocument = null;
		for (PageDoc document : documentList) {
			if (!document.isAllocated()) {
				double documentDistance = distance.calcDistance(document, this);
				if (documentDistance > furthestDistance) {
					furthestDistance = documentDistance;
					furthestDocument = document;
				}
			}
		}
		return furthestDocument;
	}

	/** Find cluster whose centroid is closest to a document. */
	public Cluster findNearestCluster(DistanceMetric distance, PageDoc document) {
		Cluster nearestCluster = null;
		double nearestDistance = Double.MAX_VALUE;
		for (Cluster cluster : clusters) {
			double clusterDistance = distance.calcDistance(document, cluster);
			if (clusterDistance < nearestDistance) {
				nearestDistance = clusterDistance;
				nearestCluster = cluster;
			}
		}
		return nearestCluster;
	}

	@Override
	public Iterator<Cluster> iterator() {
		return clusters.iterator();
	}

	/** Return the number of clusters in this ClusterList. */
	public int size() {
		return clusters.size();
	}

	/**
	 * Sort order of documents within each cluster, then sort order of clusters within ClusterList.
	 */
	private void sort() {
		for (Cluster cluster : this) {
			cluster.sort();
		}
		Collections.sort(clusters);
	}

	/**
	 * Display clusters in sorted order.
	 */
	@Override
	public String toString() {
		sort();
		StringBuilder sb = new StringBuilder();
		int clusterIndex = 1;
		for (Cluster cluster : clusters) {
			sb.append("Cluster ");
			sb.append(clusterIndex++);
			sb.append("\n");
			sb.append(cluster);
		}
		return sb.toString();
	}

	/**
	 * Update centroids of all clusters within ClusterList.
	 */
	public void updateCentroids() {
		for (Cluster cluster : clusters) {
			cluster.updateCentroid();
		}
	}
}
