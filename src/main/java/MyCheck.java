import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author:jie.tang on 2019-04-22 17:15
 * desc:
 */
public class MyCheck {

    public static ConcurrentHashMap<String,Doc> STAND_DOC_MAP = new ConcurrentHashMap<String,Doc>(30000);
    public static ConcurrentSkipListSet<Doc> DOC_SET = new ConcurrentSkipListSet<Doc>();
    // you must rewrite this function
    public void init(Vector<String> docList) {
        AtomicInteger docIndex = new AtomicInteger(0);
        docList.parallelStream().forEach(currDoc ->{
            Doc doc = new Doc(docIndex.addAndGet(1), currDoc.length());
            DOC_SET.add(doc);
            Map<String, Long> charMap = Arrays.asList(currDoc.trim().split("")).parallelStream().collect(Collectors.groupingBy(str -> str, Collectors.counting()));
            doc.setCharCount(charMap);

        });



    }


    // you must rewrite this function
    // checking
    // IN tiezi : doc string
    // OUT : 1=>hit doc
    //       0=>miss doc
    public int check(char[] info, int len ) {
        return 0;
    }

    public static class Doc{
        private int docId;

        private int docLenth;

        private Map<String,Long> charCount;

        public Doc(int docId, int docLenth) {
            this.docId = docId;
            this.docLenth = docLenth;
            this.charCount = charCount;
        }

        public int getDocId() {
            return docId;
        }

        public void setDocId(int docId) {
            this.docId = docId;
        }

        public int getDocLenth() {
            return docLenth;
        }

        public void setDocLenth(int docLenth) {
            this.docLenth = docLenth;
        }

        public Map<String, Long> getCharCount() {
            return charCount;
        }

        public void setCharCount(Map<String, Long> charCount) {
            this.charCount = charCount;
        }
    }

}
