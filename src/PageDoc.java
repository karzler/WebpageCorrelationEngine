import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;

public class PageDoc implements Comparable<PageDoc> {
	private final String title;
	private URL url;
	private final String contents;
	private final long id;
	private boolean allocated;
	private Vector histogram;
	private Vector vector;
	private int numFeatures;
	
	private void URLValidator(String url) {		
		try{
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL exception encountered.\n"
					+ "Please check if the url entered is correct or not.\n");
			e.printStackTrace();
		}
	}
	public void setURL(String url) {
		URLValidator(url);
	}	
	/*
	 * Getter method for retrieving url.
	 */
	public URL getURL() {
		return this.url;
	}
	
	public PageDoc (String url, int id)
	{
		URLValidator(url);
		this.contents = getDocument().text();
		this.title = getDocument().title();
		this.id = id;
	}
	public PageDoc(long id, String contents, String title, String url) {
		this.id = id;
		this.contents = contents;
		this.title = title;
		URLValidator(url);
	}

	/** Clear page as not being allocated to a cluster. */
	public void clearIsAllocated() {
		allocated = false;
	}

	/** sorted by ID. */
	@Override
	public int compareTo(PageDoc document) {
		if (id > document.getId()) {
			return 1;
		} else if (id < document.getId()) {
			return -1;
		} else {
			return 0;
		}
	}

	/** Getter method for the contents. */
	public String getContents() {
		return contents;
	}

	/** Get word histogram. */
	public Vector getHistogram() {
		return histogram;
	}

	/** Get the page doc ID. */
	public long getId() {
		return id;
	}

	/** Getter method for number of features in feature vector. */
	public int getNumFeatures() {
		return numFeatures;
	}

	/**
	 * Getter method for feature vector for a page doc. 
	 */
	public Vector getVector() {
		return vector;
	}

	/** Determine whether document has been allocated to a cluster. */
	public boolean isAllocated() {
		return allocated;
	}

	/** Set the word histogram for a document. */
	public void setHistogram(Vector histogram) {
		this.histogram = histogram;
	}

	/** Mark document as having been allocated to a cluster. */
	public void setIsAllocated() {
		allocated = true;
	}

	/**
	 * Set the feature vector for a document.
	 */
	public void setVector(Vector vector) {
		this.vector = vector;
		this.numFeatures = vector.size();
	}
	public org.jsoup.nodes.Document getDocument ()
	{
		try
		{
			return Jsoup.connect(url.toString()).get();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1223234235);
			return null;
		}
	}

	@Override
	public String toString() {
		return "Page Doc: " + id + ", Title: " + title;
	}
}
