package ch10;

import ch10.util.Http;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressRetrieverTest {
    @Mock
    private Http httpMock;
    @InjectMocks
    private AddressRetriever retrieverMock;

    @BeforeEach
    public void createRetriever() {
        retrieverMock = new AddressRetriever(httpMock);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void answerAppropriateAddressForValidCoordinates() throws IOException, ParseException {
//        Http http = (String url) -> {
//            if (!url.contains("lat=38.000000&lon=-104.000000")) fail("url " + url + " does not contain correct params");
//            return "{\"address\":{"
//                    + "\"house_number\":\"324\","
//                    + "\"road\":\"North Tejon Street\","
//                    + "\"city\":\"Colorado Springs\","
//                    + "\"state\":\"Colorado\","
//                    + "\"postcode\":\"80903\","
//                    + "\"country_code\":\"us\"}"
//                    + "}";
//        };

        Http http = mock(Http.class);
        when(http.get(contains("lat=38.000000&lon=-104.000000")))
                .thenReturn(
                "{\"address\":{"
                + "\"house_number\":\"324\","
                + "\"road\":\"North Tejon Street\","
                + "\"city\":\"Colorado Springs\","
                + "\"state\":\"Colorado\","
                + "\"postcode\":\"80903\","
                + "\"country_code\":\"us\"}"
                + "}");

        AddressRetriever addressRetriever = new AddressRetriever(http);

        Address address = addressRetriever.retrieve(38.0, -104.0);

        assertThat(address.houseNumber).isEqualTo("324");
        assertThat(address.road).isEqualTo("North Tejon Street");
        assertThat(address.city).isEqualTo("Colorado Springs");
        assertThat(address.state).isEqualTo("Colorado");
        assertThat(address.zip).isEqualTo("80903");
    }

    @Test
    public void answerAppropriateAddressForValidCoordinatesUsingMockito() throws IOException, ParseException {
        when(httpMock.get(contains("lat=38.000000&lon=-104.000000")))
                .thenReturn(
                        "{\"address\":{"
                                + "\"house_number\":\"324\","
                                + "\"road\":\"North Tejon Street\","
                                + "\"city\":\"Colorado Springs\","
                                + "\"state\":\"Colorado\","
                                + "\"postcode\":\"80903\","
                                + "\"country_code\":\"us\"}"
                                + "}");

        Address address = retrieverMock.retrieve(38.0, -104.0);

        assertThat(address.houseNumber).isEqualTo("324");
        assertThat(address.road).isEqualTo("North Tejon Street");
        assertThat(address.city).isEqualTo("Colorado Springs");
        assertThat(address.state).isEqualTo("Colorado");
        assertThat(address.zip).isEqualTo("80903");
    }

    @Test
    public void returnsAppropriateAddressForValidCoordinates()
            throws IOException, ParseException {
        Http http = new Http() {
            @Override
            public String get(String url) throws IOException {
                return "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        // ...
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}";
            }};
        AddressRetriever retriever = new AddressRetriever(http);

        Address address = retriever.retrieve(38.0,-104.0);

        assertThat(address.houseNumber).isEqualTo("324");
        assertThat(address.road).isEqualTo("North Tejon Street");
        assertThat(address.city).isEqualTo("Colorado Springs");
        assertThat(address.state).isEqualTo("Colorado");
        assertThat(address.zip).isEqualTo("80903");
    }
}