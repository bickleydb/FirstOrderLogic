import java.util.*;

public class ArgumentList implements Iterable<HashSet<String>> {
	public int numArgs;
	public int [] numOptionsForEachArg;
	public ArrayList<String> [] argOptions;
	public int totalOptions;
	
	public static StringBuffer buffer = new StringBuffer(100);
	
	@SuppressWarnings("unchecked")
	public ArgumentList(ArrayList<String> ... args)
	{
		numArgs = args.length;
		numOptionsForEachArg = new int [numArgs];
		argOptions = new ArrayList [numArgs];
		totalOptions = 1;
		for (int i=0; i<numArgs; i++) {
//			System.out.println(args[i]);
			argOptions[i] = (ArrayList<String>)args[i].clone();
			numOptionsForEachArg[i] = args[i].size();
			totalOptions *= numOptionsForEachArg[i];
		}
	}
	public Iterator<HashSet<String>> iterator()
	{
		return new ArgumentListIterator(this);
	}
	public static ArrayList<String> createSet(String ... items)
	{
		ArrayList<String> set = new ArrayList<String>();
		for (String str : items) {
			set.add(str);
		}
		return set;
	}
	public int getNumber()
	{ return totalOptions; }
	public String getNth(int N)
	{
		buffer.setLength(0);
		
		for (int i=numArgs-1; i>=0; i--) {
			if (i < numArgs - 1)
				buffer.insert(0, ";");
			buffer.insert(0, argOptions[i].get(N%numOptionsForEachArg[i]));
			N /= numOptionsForEachArg[i];
		}
		
		return buffer.toString();
	}
}
