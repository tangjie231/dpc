import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Check {

	private Map<Integer,long[]> docMap = new HashMap<>(5000);
	private Map<Integer,int[]> docIntMap = new HashMap<>(5000);
	private short[] docLArray = new short[1000];
	AtomicInteger count = new AtomicInteger(0);
	private int checkIndex = 0;

	public void init(Vector<String> docList) {
		for (int i = 0; i < docList.size(); i++) {
			count.set(0);
			System.out.println("hand:"+i);

			try {
				final int index = i;
				String curr = docList.get(index);

				curr.chars().distinct().forEach(c -> {
					long[] longs = docMap.get(c);
					if(Objects.isNull(longs)){
						longs = new long[1000/64+1];
						docMap.put(c, longs);
					}
					putLocation(longs,index);
					count.incrementAndGet();
				});

//				curr = null;
				docLArray[index] = (short) Math.ceil(count.get()*0.6);
				//docList.remove(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		docMap.forEach((k,v)->{
			List<Integer> positions = new LinkedList<>();
			for (int i = 0; i < v.length; i++) {
				long l = v[i];
				if(l>0){
					int position = 0;
					while (l>0){
						if((l&1) == 1){
							positions.add((64 * i) + position);
						}
						l = l>>1;
						position++;
					}
				}

			}
			int[] docArrays = new int[positions.size()];

			Iterator<Integer> iterator = positions.iterator();
			int index = 0;
			while (iterator.hasNext()){
				docArrays[index++] = iterator.next();
			}

			docIntMap.put(k, docArrays);
			//positions = null;
		});

	}

	private void putLocation(long[] longs,int location ){
		longs[location>>6] |= (1L << location);

	}

	private void calLocation(long[] longs,short[] rsArray){
		for (int i = 0; i < rsArray.length; i++) {
			if((longs[i>>6] & (1L << i))!=0){
				rsArray[i]++;
			}
		}
	}


	public int check(char[] info, int len ) {
		System.out.println("run here"+checkIndex++);
		short[] rsArray = new short[1000];


		/*int[] ints = new String(info).chars().distinct().toArray();
		long[][] lineLongs = new long[ints.length][];
		for (int i = 0; i < ints.length; i++) {
			lineLongs[i] = docMap.get((char) ints[i]);
		}

		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < lineLongs.length; j++) {
				long[] lineLong = lineLongs[j];
				if(lineLong == null){
					continue;
				}

				if(Objects.isNull(rsArray[i])) {
					rsArray[i] = new long[lineLongs.length / 64 +1];
				}

				long l  = lineLong[i >> 6];

				if((l & (1L << i))!=0){
					putLocation(rsArray[i],j);
				}

			}
		}

		for (int i = 0; i < rsArray.length; i++) {
			long[] longs = rsArray[i];
			int count = 0;
			for (int j = 0; j < longs.length; j++) {
				long l = longs[j];
				if(l == 0){
					continue;
				}
				while (l>0){
					count++;
					l &= l-1;
				}

			}
			if(docLArray[i]<=count){
				return 1;
			}


		}*/


		new String(info).chars().distinct().forEach(cx -> {
			/*long[] longs = docMap.get(cx);
			if(Objects.nonNull(longs)){
				calLocation(longs,rsArray);
			}*/
			int[] ints = docIntMap.get(cx);
			if(Objects.nonNull(ints)) {
				for (int i = 0; i < ints.length; i++) {
					rsArray[ints[i]]++;
				}
			}
		});
		for (int i = 0; i < rsArray.length; i++) {
			int hitLength = rsArray[i];
			if(hitLength != 0  && docLArray[i]<=hitLength){
				return 1;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		Check check = new Check();
		long[] longs = new long[16];
		check.putLocation(longs, 125);
		check.putLocation(longs,111);
		check.putLocation(longs,23);
		check.putLocation(longs,0);



		System.out.println((longs[125 >> 6] & (1L << 125 )) != 0);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(j==5){
					continue;
				}
				System.out.println(i+""+j);
			}
		}

	}



}

