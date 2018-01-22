package eugene.com.transferwise;

import android.app.Application;

import eugene.com.livelibrary.AppExecutors;
import eugene.com.transferwise.api.PlacesClient;
import eugene.com.transferwise.db.PlacesDatabase;

public class PlacesApplication extends Application {
    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    public PlacesDatabase getDatabase() {
        return PlacesDatabase.getInstance(this);
    }

    public PlacesClient getPlacesClient() {
        return PlacesClient.getInstance();
    }
}
