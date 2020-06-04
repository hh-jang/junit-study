package ch11.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import static org.assertj.core.api.Assertions.*;

public class SearchTest {
    private static final String ANY_TITLE = "1";
    private InputStream inputStream;

    @BeforeEach
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @AfterEach
    public void closeResources() throws IOException{
        inputStream.close();
    }

    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws IOException {
        // GIVEN
        inputStream =
                streamOn("rest of text here" +
                        "1234567890search term1234567890" +
                        "more rest of text");
        Search search = new Search(inputStream, "search term", ANY_TITLE);
        search.setSurroundingCharacterCount(10);

        // WHEN
        search.execute();
        List<Match> matches = search.getMatches();
        Match match = matches.get(0);

        // THEN
//        assertThat(matches).isNotNull();      // 프로덕션 코드에서는 null 검사가 맞다. 그러나 이건 테스트다! 알아서 예외 던짐 알아서 체크하라고 테스트 실패할꺼다!
        assertThat(matches.size() >= 1).isTrue();
        assertThat(match.searchString).isEqualTo("search term");
        assertThat(match.surroundingContext).isEqualTo("1234567890search term1234567890");
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws IOException {
        // GIVEN
        inputStream = streamOn("any text");
        Search search = new Search(inputStream, "text that doesn't match", ANY_TITLE);

        // WHEN
        search.execute();

        // THEN
        assertThat(search.getMatches().size()).isEqualTo(0);
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}