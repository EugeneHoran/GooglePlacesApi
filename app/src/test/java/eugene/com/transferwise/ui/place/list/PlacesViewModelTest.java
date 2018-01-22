package eugene.com.transferwise.ui.place.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.db.entity.Place;
import eugene.com.transferwise.repository.PlacesRepository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class PlacesViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private PlacesRepository placesRepository;
    private PlacesViewModel placesViewModel;

    @Before
    public void setup() {
        placesRepository = mock(PlacesRepository.class);
        placesViewModel = new PlacesViewModel(placesRepository);
    }

    @Test
    public void testNull() {
        assertThat(placesViewModel.getPlaceRepository(), notNullValue());
        verify(placesRepository, never()).loadLocalRestaurants();
    }

    @Test
    public void testCreationOfViewModel() throws Exception {
        Observer<Resource<List<Place>>> observer = mock(Observer.class);
        placesViewModel.getPlaceRepository().observeForever(observer);
        verify(placesRepository, never()).loadLocalRestaurants();
        verifyNoMoreInteractions(observer);
        verifyNoMoreInteractions(placesRepository);
    }

    @Test
    public void testRefresh() {
        // Init ViewModel creation
        placesViewModel.getPlaceRepository().observeForever(mock(Observer.class));
        reset(placesRepository);
        // Test refresh
        placesViewModel.refresh();
        verify(placesRepository, times(1)).loadLocalRestaurants();
    }
}