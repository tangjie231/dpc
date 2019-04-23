import java.util.*;


public class Check {

	private final static double REPEAT_RATE = 0.6f;
	private static int length = 30;
	// you can add your data-structure here
	private short[] charList = new short[200000];
	private short nextCode = 0;

	private int countN = 0;

	private int max = 0;
	private Doc[] docArray;
	private List<Doc> docs = new LinkedList<>();

	private int count=0;


	private static int countOnes(long num) {
		int res = 0;
		while (num != 0) {
			num &= num - 1;
			res++;
		}
		return res;
	}

	private static long[] initLongArray() {
		return new long[length];
	}

	private static long count(long[] docCode) {
		return Arrays.stream(docCode).map(Check::countOnes).sum();
	}

	private int getCharCode(char character) {
		short code = charList[character];
		if (code == 0) {
			charList[character] = ++nextCode;
			return nextCode;
		}
		return code;
	}

	private void toOne(long[] source, int index) {
		int i = 0;
		while (index > 64) {
			index -= 64;
			i++;
		}
		if (i >= length) {
			return;
		}
		source[i] = source[i] | 1L << index;
	}

	private void setupDoc(String docStr) {
		long[] docCode = initLongArray();
		for (int i = 0; i < docStr.length(); i++) {
			char c = docStr.charAt(i);
			toOne(docCode, getCharCode(c));
		}
		Doc doc = new Doc(docCode, count(docCode));
		docs.add(doc);
	}

	// you must rewrite this function
	public void init(Vector<String> docList) {

		Set<String> set = new HashSet<>(docList);
		set.forEach(this::setupDoc);

		max = docs.stream().map(Doc::getRepeatCount).max(Integer::compareTo).orElse(0);
		docArray = new Doc[max + 1];

		docs.forEach(doc -> {
			Doc d = docArray[doc.repeatCount];
			if (d == null) {
				docArray[doc.repeatCount] = doc;
			} else {
				d.addDoc(doc);
			}
		});
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			String test = docList.get(random.nextInt(docList.size()));
			check(test.toCharArray(), test.length());
		}

		System.out.println("init finished");
		count = 0;
	}

	// you must rewrite this function
	// checking
	// IN tiezi : doc string
	// OUT : 1=>hit doc
	//       0=>miss doc
	public int check(char[] info, int infoLen) {
		System.out.println(++count);
		long[] infoCode = initLongArray();
		for (int i = 0; i < infoLen; i++) {
			int charCode = getCharCode(info[i]);
			toOne(infoCode, charCode);
		}
		long infoCount = count(infoCode);
//        Doc.n = 0;
		for (int i = 0; i < infoCount && i < max; i++) {
			Doc r = docArray[i];
			if (r == null) {
				continue;
			}
			if (r.match(infoCode, infoCount)) {
//                countN += Doc.n;
//                System.out.println(countN + ":" + Doc.n);
				return 1;
			}
		}
//        countN += Doc.n;
//        System.out.println(countN + ":" + Doc.n);
		return 0;
	}

	private static class Doc {
		static int n = 0;
		long[] docCode;
		long docCount;
		int repeatCount;
		Doc parentDoc = null;
		List<Doc> subDocs = null;

		public Doc(long[] docCode, long docCount) {
			this.docCode = docCode;
			this.docCount = docCount;
			this.repeatCount = (int) Math.ceil(count(docCode) * REPEAT_RATE);
		}

		public double distance(Doc doc) {
			double matchCount = inter(doc.docCode);
			double dis = (doc.docCount - matchCount) * (docCount - matchCount) * Math.pow(10, Math.abs(doc.repeatCount - repeatCount));
//            System.out.println(dis);
			return dis;
		}

		public void addDoc(Doc doc) {
			Doc parent = parentDoc;
			int floor = 1;
			while (parent != null) {
				floor++;
				parent = parent.parentDoc;
			}

			if (subDocs == null) {
				addSubDoc(doc);
			} else {
				int finalFloor = floor;
				Doc pDoc = subDocs.stream()
						.filter(d -> d.distance(doc) < 10000)
						.min(Comparator.comparingDouble(a -> a.distance(doc))).orElse(null);
				if (pDoc == null) {
					addSubDoc(doc);
				} else {
					pDoc.addDoc(doc);
				}
			}
		}

		private void addSubDoc(Doc doc) {
			if (subDocs == null) {
				subDocs = new LinkedList<>();
				subDocs.add(new Doc(docCode, docCount));
			}
			subDocs.add(doc);
			subDocs.forEach(d -> d.parentDoc = this);
			update();
		}

		private void update() {
			docCode = initLongArray();
			subDocs.stream().reduce((a, b) -> {
				for (int i = 0; i < length; i++) {
					docCode[i] = a.docCode[i] | b.docCode[i];
				}
				this.repeatCount = Math.min(a.repeatCount, b.repeatCount);
				return this;
			});
			docCount = count(docCode);
			if (parentDoc != null) {
				parentDoc.update();
			}
		}

		private int inter(long[] infoCode) {
			return countOnes(docCode[0] & infoCode[0])
					+ countOnes(docCode[1] & infoCode[1])
					+ countOnes(docCode[2] & infoCode[2])
					+ countOnes(docCode[3] & infoCode[3])
					+ countOnes(docCode[4] & infoCode[4])
					+ countOnes(docCode[5] & infoCode[5])
					+ countOnes(docCode[6] & infoCode[6])
					+ countOnes(docCode[7] & infoCode[7])
					+ countOnes(docCode[8] & infoCode[8])
					+ countOnes(docCode[9] & infoCode[9])
					+ countOnes(docCode[10] & infoCode[10])
					+ countOnes(docCode[11] & infoCode[11])
					+ countOnes(docCode[12] & infoCode[12])
					+ countOnes(docCode[13] & infoCode[13])
					+ countOnes(docCode[14] & infoCode[14])
					+ countOnes(docCode[15] & infoCode[15])
					+ countOnes(docCode[16] & infoCode[16])
					+ countOnes(docCode[17] & infoCode[17])
					+ countOnes(docCode[18] & infoCode[18])
					+ countOnes(docCode[19] & infoCode[19])
					+ countOnes(docCode[20] & infoCode[20])
					+ countOnes(docCode[21] & infoCode[21])
					+ countOnes(docCode[22] & infoCode[22])
					+ countOnes(docCode[23] & infoCode[23])
					+ countOnes(docCode[24] & infoCode[24])
					+ countOnes(docCode[25] & infoCode[25])
					+ countOnes(docCode[26] & infoCode[26])
					+ countOnes(docCode[27] & infoCode[27])
					+ countOnes(docCode[28] & infoCode[28])
					+ countOnes(docCode[29] & infoCode[29]);
		}

		public boolean match(long[] infoCode, long infoCount) {
//            n++;
			if (infoCount < repeatCount) {
				return false;
			} else {
				int count = inter(infoCode);
				boolean match = count >= repeatCount;
				if (!match) {
					return false;
				} else if (subDocs == null) {
					return true;
				} else {
					for (Doc doc : subDocs) {
						if (doc.match(infoCode, infoCount)) {
							return true;
						}
					}
					return false;
				}
			}
		}

		public long[] getDocCode() {
			return docCode;
		}

		public long getDocCount() {
			return docCount;
		}

		public int getRepeatCount() {
			return repeatCount;
		}

		public Doc getParentDoc() {
			return parentDoc;
		}

		public List<Doc> getSubDocs() {
			return subDocs;
		}
	}

}

