import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Check {

	private Map<Character,List<Integer>> docMap = new HashMap<>(50000);
	private short[] docLArray = new short[100000];
	AtomicInteger count = new AtomicInteger(0);
	private Set<Character> docCharSet = new HashSet<>(1024);

	public void init(Vector<String> docList) {
		for (int i = 0; i < docList.size(); i++) {
			count.set(0);
			System.out.println("hand:"+i);

			if(i == 3000){
				System.out.println("run here");
			}
			try {
				final int index = i;
				String curr = docList.get(index);

				char[] chars = curr.toCharArray();
				for (int j = 0; j < chars.length; j++) {
					char currC = chars[j];
					if(docCharSet.add(currC)){
						List<Integer> list = docMap.get(currC);
						if(Objects.isNull(list)){
							list = new LinkedList<>();
							docMap.put(currC, list);
						}
						list.add(index);
						count.incrementAndGet();
					}
				}
				curr = null;
				docLArray[index] = (short) Math.ceil(count.get()*0.6);
				docCharSet.clear();
				//docList.remove(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(docMap);

	}


	public int check(char[] info, int len ) {
		short[] rsArray = new short[1000000];
		new String(info).chars().distinct().forEach(cx -> {
			char currCx = (char)cx;
			List<Integer> list = docMap.get(currCx);
			if(Objects.nonNull(list)) {
				list.forEach(docIndex -> {
					rsArray[docIndex]++;
				});
			}
		});
		for (int i = 0; i < rsArray.length; i++) {
			short hitLength = rsArray[i];
			if(hitLength != 0  && docLArray[i]<=hitLength){
				return 1;
			}
		}
		return 0;
	}



}

