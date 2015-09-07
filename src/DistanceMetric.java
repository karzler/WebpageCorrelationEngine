
public abstract class DistanceMetric {
	public double calcDistance(Cluster cluster1, Cluster cluster2) {
		return calcDistance(cluster1.getCentroid(), cluster2.getCentroid());
	}
	public double calcDistance(PageDoc pageDoc, Cluster cluster) {
		return calcDistance(pageDoc.getVector(), cluster.getCentroid());
	}

	public double calcDistance(PageDoc PageDoc, ClusterList clusterList) {
		double distance = Double.MAX_VALUE;
		for (Cluster cluster : clusterList) {
			distance = Math.min(distance, calcDistance(PageDoc, cluster));
		}
		return distance;
	}

	protected abstract double calcDistance(Vector vector1, Vector vector2);
}
