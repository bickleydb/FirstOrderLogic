import java.util.*;

public class GrayCodeEnumerator {
	static int twoToThePower(int n)
	{ return (int)Math.pow(2, n); }

	private static void fill(ArrayList<Integer> bits, int bit)
	{
		if (bit == 0) {
			bits.add(0);
		} else {
			fill(bits, bit-1);
			bits.add(bit);
			fill(bits, bit-1);
		}
	}
	public static ArrayList<Integer> getSequence(int nBits)
	{
		ArrayList<Integer> bits = new ArrayList<Integer>();
		fill(bits, nBits-1);
		return bits;
	}
	public static String [] checkIt(int nBits, ArrayList<Integer> bits)
	{
		HashSet<Integer> items = new HashSet<Integer>();
		
		String [] result = new String [nBits];
		StringBuffer str = new StringBuffer(2*nBits);
		int pos = 0;
		
		for (int i=0; i<nBits; i++) str.append('0');
		result[pos++] = str.toString();
		
		for (int b : bits) {
			str.setCharAt(b, str.charAt(b) == '0' ? '1' : '0');
			result[pos++] = str.toString();
			items.add(Integer.parseUnsignedInt(str.toString(), 2));
		}
		System.out.println(items.size());
		
		return result;
	}
}
