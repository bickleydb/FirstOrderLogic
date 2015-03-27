import java.util.*;

public class World implements Iterable<HashSet<String> []>{
	public HashSet<String> [] trueForPredicate;
	public int numPreds;
	public ArgumentList [] argLists;
	public static StringBuffer buffer = new StringBuffer(1000);
	
	@SuppressWarnings("unchecked")
	public World(ArgumentList ... argumentLists)
	{
		int np = argumentLists.length;
		argLists = argumentLists;	// probably need deep copy
		numPreds = np;
		trueForPredicate = new HashSet [np];
		for (int i=0; i<np; i++) {
			trueForPredicate[i] = new HashSet<String>();
		}
	}
	public Iterator<HashSet<String> []> iterator()
	{
		return new WorldIterator(this);
	}
	public String toString()
	{
		buffer.setLength(0);
		for (int i=0; i<numPreds; i++) {
			if (i != 0)
				buffer.append("----------------");
			for (String str : trueForPredicate[i]) {
				buffer.append(str);
			}
		}
		return buffer.toString();
	}
}