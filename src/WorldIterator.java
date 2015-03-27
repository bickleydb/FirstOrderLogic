import java.util.*;


public class WorldIterator implements Iterator<HashSet<String>[]> {
	private World world;
	private HashSet<String> [] preds;
	private ArgumentListIterator [] iters;
	
	@SuppressWarnings("unchecked")
	public WorldIterator(World w)
	{
		world = w;
		preds = new HashSet [w.numPreds]; 
		iters = new ArgumentListIterator [w.numPreds];
		for (int i=0; i<preds.length; i++) {
			iters[i] = new ArgumentListIterator(w.argLists[i]);
			if (i > 0)
				iters[i].next();
			preds[i] = iters[i].trueValues;
		}
	}
	
	public boolean hasNext()
	{
		for (ArgumentListIterator i : iters)
			if (i.hasNext())
				return true;
		return false;
	}
	
	public HashSet<String> [] next()
	{
		for (int i=0; i<preds.length; i++) {
			if (iters[i].hasNext()) {
				iters[i].next();
				break;
			} else {
				iters[i].reset();
				iters[i].next();
			}
		}
		return preds;
	}
}
