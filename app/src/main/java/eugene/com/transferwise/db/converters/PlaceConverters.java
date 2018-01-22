package eugene.com.transferwise.db.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import eugene.com.transferwise.db.entity.DetailsOpeningHours;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.db.entity.Photo;

public class PlaceConverters {

    @TypeConverter
    public static DetailsOpeningHours detailHoursJsonToList(String reviewString) {
        if (reviewString == null) {
            return null;
        }
        Type type = new TypeToken<DetailsOpeningHours>() {
        }.getType();
        return new Gson().fromJson(reviewString, type);
    }

    @TypeConverter
    public static String detailHoursJson(DetailsOpeningHours details) {
        return new Gson().toJson(details);
    }


    @TypeConverter
    public static List<DetailsReview> reviewJsonToList(String reviewString) {
        if (reviewString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<DetailsReview>>() {
        }.getType();
        return new Gson().fromJson(reviewString, listType);
    }

    @TypeConverter
    public static String detailListToJson(List<DetailsReview> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<Photo> photosJsonToList(String photoString) {
        if (photoString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Photo>>() {
        }.getType();
        return new Gson().fromJson(photoString, listType);
    }

    @TypeConverter
    public static String photosListToJson(List<Photo> list) {
        return new Gson().toJson(list);
    }
}
