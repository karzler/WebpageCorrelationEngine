import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * @author Mohit Mishra
 * Last Updated: Sept. 04, 2015.
 * 
 */

public class CorrelationEngine {
	private static final int CLUSTERING_ITERATIONS = 3;
	private static final double CLUSTERING_THRESHOLD = 0.5;
	private static final int NUM_FEATURES = 10000;

	/**
	 * Cluster the page docs. 
	 */
	public static void main(String[] args) throws IOException {
		// Test Cases
		String[] urls = new String[7];
		urls[0] = "http://www.spirithalloween.com/section/kids/1563.uts";
		urls[1] = "http://www.partycity.com/category/halloween+costumes.do";
		urls[2] = "http://www.amazon.com/toys-halloween-costumes/b?node=166639011";
		urls[3] = "http://www.costumeexpress.com/m/cx_kids/77";
		urls[4] = "http://www.target.com/c/kids-costumes-halloween-holiday/-/N-54x8l";
		urls[5] = "http://www.brightedge.com/blog";
		urls[6] = "http://www.google.com";
		PageDocList documentList = new PageDocList();
		int id = 0;
		HashMap <Integer, String> id_url = new HashMap<>();
		for (String pageURL : urls)
		{
			documentList.add(new PageDoc (pageURL, id));
			id_url.put(id, pageURL);
			id++;
		}
		BuildFeatureVector encoder = new TF_IDF(NUM_FEATURES);
		encoder.buildFeatureVector(documentList);
		DistanceMetric distance = new CosineSimilarity();
		ClusterEngine clusterer = new KMeansClustering(distance, CLUSTERING_THRESHOLD, CLUSTERING_ITERATIONS);
		ClusterList clusterList = clusterer.cluster(documentList);
		// Print Page ID - URL map
		System.out.println("(Page Doc ID, Page URL)");
		for (Map.Entry<Integer, String> entry : id_url.entrySet()) {
		    int key = entry.getKey();
		    String URL = entry.getValue();
		    System.out.println("\t("+key+", "+URL+")");
		}
		System.out.println(clusterList);
		System.out.println("==============================================");
		//TO DO :(
		//System.out.println("Correlation Ranking");
		//System.out.println("\n");
		
		
	}
}