
/**
 *  Class for storing a collection of pages to be clustered. 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PageDocList implements Iterable<PageDoc> {
	private final List<PageDoc> pageDocs = new ArrayList<PageDoc>();
	private int numFeatures;

	/** Construct an empty DocumentList. */
	public PageDocList() {
	}

	/** Add a document to the DocumentList. */
	public void add(PageDoc document) {
		pageDocs.add(document);
	}

	/** Clear all documents from the DocumentList. */
	public void clear() {
		pageDocs.clear();
		
	}

	/** Clear all pages as not being allocated to a cluster. */
	public void clearIsAllocated() {
		for (PageDoc document : pageDocs) {
			document.clearIsAllocated();
		}
	}

	/** Geter method a particular page doc */
	public PageDoc get(int index) {
		return pageDocs.get(index);
	}

	/** Getter method for the number of features used*/
	public int getNumFeatures() {
		return numFeatures;
	}

	/** Determine whether DocumentList is empty. */
	public boolean isEmpty() {
		return pageDocs.isEmpty();
	}

	@Override
	public Iterator<PageDoc> iterator() {
		return pageDocs.iterator();
	}

	/** Set the number of features used to encode each document. */
	public void setNumFeatures(int numFeatures) {
		this.numFeatures = numFeatures;
	}

	/** Get the number of documents within the DocumentList. */
	public int size() {
		return pageDocs.size();
	}

	/** Sort the documents within the DocumentList by document ID. */
	public void sort() {
		Collections.sort(pageDocs);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PageDoc document : pageDocs) {
			sb.append("  ");
			sb.append(document.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
