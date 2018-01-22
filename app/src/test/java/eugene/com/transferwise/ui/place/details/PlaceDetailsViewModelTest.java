package eugene.com.transferwise.ui.place.details;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import eugene.com.transferwise.model.PlaceDetailsData;
import eugene.com.transferwise.repository.DetailsRepository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class PlaceDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private DetailsRepository detailsRepository;
    private PlaceDetailsViewModel detailsViewModel;

    @Before
    public void setup() {
        detailsRepository = mock(DetailsRepository.class);
        detailsViewModel = new PlaceDetailsViewModel(detailsRepository);
    }

    @Test
    public void testNull() {
        assertThat(detailsViewModel.details(), notNullValue());
        verify(detailsRepository, never()).loadPlaceDetails(detailsViewModel.placeId.getValue());
    }

    @Test
    public void testCreationOfViewModel() throws Exception {
        Observer<PlaceDetailsData> observer = mock(Observer.class);
        detailsViewModel.details().observeForever(observer);
        verifyNoMoreInteractions(observer);
        verifyNoMoreInteractions(detailsRepository);
    }

    @Test
    public void testSetPlaceId() throws Exception {
        Observer<PlaceDetailsData> observer = mock(Observer.class);
        detailsViewModel.details().observeForever(observer);

        detailsViewModel.setPlaceId("test");
        verify(detailsRepository).loadPlaceDetails("test");

        reset(detailsRepository);

        detailsViewModel.setPlaceId("test2");
        verify(detailsRepository).loadPlaceDetails("test2");

        detailsViewModel.details().removeObserver(observer);

        detailsViewModel.setPlaceId("test3");
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void testNullPlaceId() {
        Observer<PlaceDetailsData> observer = mock(Observer.class);
        detailsViewModel.details().observeForever(observer);
        detailsViewModel.setPlaceId("Testing");
        reset(detailsRepository);
        detailsViewModel.setPlaceId(null);
        verifyNoMoreInteractions(detailsRepository);
    }
}