import java.util.*;

public class Tester {
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		/*
		final int N = 2;
		String [] items = GrayCodeEnumerator.checkIt(N, seq);
		for (String s : items)
			System.out.println(s);
		*/
		
		ArrayList<String> p1 = ArgumentList.createSet("A", "B","C","D");
		ArrayList<String> p2 = ArgumentList.createSet("bart");
		ArrayList<String> p3 = ArgumentList.createSet("A","B");
		ArgumentList list1 = new ArgumentList(p1, p2, p3);

		ArrayList<String> q1 = ArgumentList.createSet("A", "B");
		ArrayList<String> q2 = ArgumentList.createSet("A", "B");
		ArgumentList list2 = new ArgumentList(q1, q2);

		ArrayList<String> r1 = ArgumentList.createSet("A", "B");
		ArrayList<String> r2 = ArgumentList.createSet("A", "B");
		ArrayList<String> r3 = ArgumentList.createSet("homer");
		ArgumentList list3 = new ArgumentList(r1,r2,r3);

		
//		ArgumentListIterator i = new ArgumentListIterator(list1);	
//		while (i.hasNext()) {
//			System.out.println(i.next());
//		}
		
		World w = new World(list1, list2, list3);
		
		int cnt = 0;
		
		for (HashSet<String> [] items : w) {
			for (HashSet<String> set : items) {
				System.out.print(set);
			}
			System.out.println();
			cnt++;
		}
		System.out.println(cnt); 
	}
	
}
