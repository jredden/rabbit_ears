package service.messages;

import java.util.ArrayList;
import java.util.List;

public class RandomName {

	private static List<Character> clist = new ArrayList<Character>();
	static{
		clist.add(new Character('a'));
		clist.add(new Character('e'));
		clist.add(new Character('i'));
		clist.add(new Character('o'));
		clist.add(new Character('u'));
	}
	private static String[] vlist = {
		"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","y","z","p","q","r","s","t","v","w","x","y","z"
	};

	public static String randomName() {
		int r0 = (int) (Math.random()*clist.size());
		int r1 = (int) (Math.random()*clist.size());
		int r2 = (int) (Math.random()*vlist.length);
		int r3 = (int) (Math.random()*vlist.length);
		int r4 = (int) (Math.random()*vlist.length);
		int r5 = (int) (Math.random()*vlist.length);
		int r6 = (int) (Math.random()*vlist.length);
		
		String name = vlist[r2]+clist.get(r0)+vlist[r3]+vlist[r4]+clist.get(r1)+vlist[r5]+vlist[r6];
		return name;
	}
}
