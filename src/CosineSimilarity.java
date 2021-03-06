
/** Class for calculating cosine distance between Vectors. */
public class CosineSimilarity extends DistanceMetric {
	@Override
	protected double calcDistance(Vector vector1, Vector vector2) {
		return 1 - vector1.innerProduct(vector2) / vector1.norm() / vector2.norm();
	}
}
