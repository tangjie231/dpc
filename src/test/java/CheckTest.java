import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:jie.tang on 2019-04-19 10:44
 * desc:
 */

public class CheckTest {

    @Test
    public void testCheck() throws Exception {
        Path path = Paths.get("D:", "IdeaProjects", "dpc", "src", "main", "java", "input", "rs.txt");
        byte[] allBytes = Files.readAllBytes(path);
        System.out.println(new String(allBytes).length());
    }

    @Test
    public void testSplit() throws Exception {
        String currDoc = "sajfldsjlfsajfldsaklfjloweero";
        Map<String, Long> collect = Arrays.asList(currDoc.trim().split("")).parallelStream().collect(Collectors.groupingBy(str -> str, Collectors.counting()));
        System.out.println(collect);

    }
}
