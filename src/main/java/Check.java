import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:jie.tang on 2019-04-22 17:15
 * desc:
 */
public class Check {

    private long[][] docMap = new long[1000][];
    private short[] docLArray = new short[1000];
    private AtomicInteger docCount = new AtomicInteger(0);
	private int checkIndex = 0;

    public void init(Vector<String> docList) {
        for (int i = 0; i < docList.size(); i++) {
            int index = i;
            docCount.set(0);

            String s = docList.get(index);
            long[] longs = new long[65535/64+1];
            docMap[index] = longs;

            s.chars().distinct().forEach(c -> {
                docCount.incrementAndGet();
                putLocation(longs,c);
            });

            docLArray[index] = (short) Math.ceil(docCount.get()*0.6);
        }


    }

    private void putLocation(long[] longs,int location ){
        longs[location>>6] |= (1L << location);

    }

    public int check(char[] info, int len ) {
		System.out.println("run"+checkIndex++);
		int length = 65535 / 64 + 1;
        long[] cLongs = new long[length];

        new String(info).chars().distinct().forEach(c ->
            putLocation(cLongs,c)
        );

        for (int i = 0; i < docMap.length; i++) {
            long[] sLongs = docMap[i];
			short docLength = docLArray[i];

            int rsCount = 0;

            for (int j = 0; j < length; j++) {
                long sLong = sLongs[j];
                long cLong = cLongs[j];

                if(sLong == 0 || cLong == 0){
                    continue;
                }

                long rs = (sLong & cLong);
                while(rs>0){
                    rsCount++;
                    rs &= (rs - 1);

					if(rsCount>= docLength){
						return 1;
					}
                }
            }

        }
        return 0;
    }

	public static void main(String[] args) {
		long l1 = Long.parseLong("101", 2);
		long l2 = Long.parseLong("111", 2);
		System.out.println(Long.toBinaryString(l1 & l2));
	}

}
