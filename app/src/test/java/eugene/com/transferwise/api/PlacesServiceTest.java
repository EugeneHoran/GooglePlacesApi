package eugene.com.transferwise.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import eugene.com.livelibrary.LiveDataCallAdapterFactory;
import eugene.com.transferwise.BuildConfig;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.db.entity.Photo;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.model.DetailsResult;
import eugene.com.transferwise.model.PlacesResult;
import eugene.com.transferwise.testing_utils.HttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static eugene.com.transferwise.testing_utils.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PlacesServiceTest {
    private static final String PLACE_NEXT_PAGE_TOKEN = "CqQCGwEAALhIxRy3PvdKd2PyMH8BN_D8SvnLmrCbhbVkppOCNSqzLEMDdPh-vFLhoRKPtDihIHYcqRe2vrEnRyY7zn-gT1qRUlhHFgSSkdTfTQJGskgGNMwgJiW34SqDUzbdrgfsShm6MKj9Eau8h9N9jD3f6whn9vHFqJCmhbutCguTzDUs5H9nMrkBgV1YxOfs5EAqaWzCoFGgC-zqkMsivV9HW2-FEzqyfLTv3uT0n5bTcRZ1I4lByp_yPfSVUmdTHlfHpfAsf5ov5jeArwtdOEhBj8Xj3XEaA3UG31gpUAyvOA-CHz0Ru4X_kzxqbmL8l9FaRXvMtVT0yokE87V2eMvkXNPGyvZViaP9_aYZTzxbmGOWVHSddj3d0oKyIuTGZMmDtxIQ7QQ6QICbRt2zAICmAesMZBoUI4qOiKbFhndePy424JbgEAJFR98";
    private static final String PLACE_DETAILS_ID = "ChIJN8TLJEgDdkgRLEFtFv_mVcs";

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private PlacesService placesService;
    private PlacesService placesDetailsService;
    private MockWebServer mockWebServer;

    @Before
    public void init() {
        mockWebServer = new MockWebServer();
        HttpClient httpClient = new HttpClient();
        placesService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient.httpClient(CLIENT.PLACE))
                .build()
                .create(PlacesService.class);
        placesDetailsService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient.httpClient(CLIENT.DETAILS))
                .build()
                .create(PlacesService.class);
    }

    @Test
    @SuppressWarnings("all")
    public void getRestaurants() throws Exception {
        enqueueResponse("mock_places.json");
        PlacesResult placesResult = getValue(placesService.getRestaurants()).body;
        List<Place> placeList;
        RecordedRequest request = mockWebServer.takeRequest();
        // Test path
        assertThat(request.getPath(), is("/nearbysearch/json?key=" + BuildConfig.PLACES_KEY + "&radius=5000&type=restaurant&location=" + BuildConfig.PLACES_LONDON_LAT_LONG));
        // Test place result values
        assertThat(placesResult, is(notNullValue()));
        assertThat(placesResult.getStatus(), is("OK"));
        assertThat(placesResult.getNextPageToken(), is(PLACE_NEXT_PAGE_TOKEN));
        assertThat(placeList = placesResult.getPlaceList(), is(notNullValue()));
        // Test place result list values
        assertThat(placesResult.getPlaceList().size(), is(2));
        // Place Item 1
        Place place1 = placeList.get(0);
        assertThat(place1.getPlaceId(), is(PLACE_DETAILS_ID));
        assertThat(place1.getPhotos().size(), is(1));
        assertThat(place1.getPhotos().get(0).getPhotoReference(), is("CmRaAAAAyJLMTbmOUoh5m0TLCOkCR89tTMRu6moafjwr3x70b7EBhvmeOPEHBhnVRo2tmrE9l-MZ1UUSUFRXs49aFF6KgRehImjSXWGr5S7Q38cuoca_HwQbnZPBNvhpiTT7IHdiEhBO8jFDiwPMMXpFq8LYpvkwGhSFPTTYQGucyOLWp9XeCM-OnTu08Q"));
        // Place item 2
        Place place2 = placeList.get(1);
        assertThat(place2.getPlaceId(), is("ChIJS6BnX6scdkgRZRvjBscyBtc"));
        assertThat(place2.getPhotos().size(), is(0));
    }

    @Test
    @SuppressWarnings("all")
    public void getDetails() throws Exception {
        enqueueResponse("mock_details.json");
        DetailsResult detailsResult = getValue(placesDetailsService.getDetails(PLACE_DETAILS_ID)).body;
        Details details;
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/details/json?placeid=" + PLACE_DETAILS_ID + "&key=" + BuildConfig.PLACES_KEY));
        assertThat(detailsResult, is(notNullValue()));
        assertThat(details = detailsResult.getResult(), is(notNullValue()));
        assertThat(details.getPlaceId(), is(PLACE_DETAILS_ID));
        assertThat(details.getRating(), is(4.2f));
        // photos
        List<Photo> photoList = details.getPhotos();
        assertThat(photoList.size(), is(10));
        assertThat(photoList.get(0).getPhotoReference(), is("CmRaAAAADIFt9y6e04paTnlZxsBm8AKMmgJGYkmO31cRNkg1Vo8NBWvc0-AoyTfTF3vTtr9H8I7gKvSU7ogzUrJvtazeGlzkHHQsvwFt5WXZCIYwfrh92pYMB4eSJQZs0JC52ZWVEhCqOomy_N1Jly1Wq2UOJ3pNGhT8X36EDXEkvdK2EoEbmRiQXTI12Q"));
        // Reviews
        List<DetailsReview> reviewList = details.getReviews();
        assertThat(reviewList.size(), is(5));
        assertThat(reviewList.get(0).getRelativeTimeDescription(), is("3 weeks ago"));
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }
}