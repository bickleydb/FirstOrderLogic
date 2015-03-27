import java.util.*;

public class ArgumentListIterator implements Iterator<HashSet<String>> {
	public ArgumentList arg;
	public HashSet<String> trueValues;
	private ArrayList<Integer> bitSeq;
	private int pos;

	public ArgumentListIterator(ArgumentList argList)
	{
		arg = argList;
		trueValues = new HashSet<String>();
		bitSeq = GrayCodeEnumerator.getSequence(argList.getNumber());
		pos = -1;
	}
	public void reset()
	{
		pos = -1;
		trueValues.clear();
	}
	public boolean hasNext()
	{
		return pos < bitSeq.size();
	}
	public HashSet<String> next()
	{
		if (pos >= 0) {
			int bit = bitSeq.get(pos);
			String str = arg.getNth(bit);
			if (trueValues.contains(str)) {
				trueValues.remove(str);
			} else {
				trueValues.add(str);				
			}
		}
		pos++;
		return trueValues;
	}
}
