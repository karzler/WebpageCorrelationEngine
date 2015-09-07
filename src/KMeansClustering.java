
import java.util.Random;

/** 
 * K-means clustering. 
 */
public class KMeansClustering implements ClusterEngine {
	private static final Random RANDOM = new Random();
	private final double clusteringThreshold;
	private final int clusteringIterations;
	private final DistanceMetric distance;          // Used Cosine Similarity

	public KMeansClustering(DistanceMetric distance, double clusteringThreshold, int clusteringIterations) {
		//  distance metric to use for clustering
		this.distance = distance;
		// threshold used to determine the number of clusters k
		this.clusteringThreshold = clusteringThreshold;
		// number of iterations to use in k-means clustering
		this.clusteringIterations = clusteringIterations;
	}

	/**
	 * Allocate any unallocated document to a "closer" cluster
	 */
	private void allocatedUnallocatedDocuments(PageDocList documentList, ClusterList clusterList) {
		for (PageDoc document : documentList) {
			if (!document.isAllocated()) {
				Cluster nearestCluster = clusterList.findNearestCluster(distance, document);
				nearestCluster.add(document);
			}
		}
	}

	/**
	 * k-means clustering
	 */
	@Override
	public ClusterList cluster(PageDocList documentList) {
		ClusterList clusterList = null;
		for (int k = 1; k <= documentList.size(); k++) {
			clusterList = processKMeansClustering(documentList, k);
			if (clusterList.calcIntraInterDistanceRatio(distance) < clusteringThreshold) {
				break;
			}
		}
		return clusterList;
	}

	
	private Cluster createClusterFromFarthestPage(PageDocList documentList,
	    ClusterList clusterList) {
		PageDoc furthestDocument = clusterList.findFurthestDocument(distance, documentList);
		Cluster nextCluster = new Cluster(furthestDocument);
		return nextCluster;
	}

	
	private Cluster createClusterWithRandomSelection(PageDocList pageDocList) {
		int randIndex = RANDOM.nextInt(pageDocList.size());
		Cluster initialCluster = new Cluster(pageDocList.get(randIndex));
		return initialCluster;
	}

	// k means clustering on the provided PageDocList for k clusters.
	private ClusterList processKMeansClustering(PageDocList documentList, int k) {
		ClusterList clusterList = new ClusterList();
		documentList.clearIsAllocated();
		clusterList.add(createClusterWithRandomSelection(documentList));
		while (clusterList.size() < k) {
			clusterList.add(createClusterFromFarthestPage(documentList, clusterList));
		}
		for (int iter = 0; iter < clusteringIterations; iter++) {
			allocatedUnallocatedDocuments(documentList, clusterList);
			clusterList.updateCentroids();
			if (iter < clusteringIterations - 1) {
				clusterList.clear();
			}
		}
		return clusterList;
	}
}
