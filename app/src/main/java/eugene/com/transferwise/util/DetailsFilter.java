package eugene.com.transferwise.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eugene.com.transferwise.R;
import eugene.com.transferwise.db.entity.Details;
import eugene.com.transferwise.db.entity.DetailsReview;
import eugene.com.transferwise.model.DetailItem;
import eugene.com.transferwise.model.DetailItemDivider;
import eugene.com.transferwise.model.DetailItemHeader;
import eugene.com.transferwise.model.DetailItemHeaderIcon;
import eugene.com.transferwise.model.DetailItemTime;

public class DetailsFilter {

    public List<DetailItem> getDetailsFilteredList(Details details) {
        if (details == null) {
            return Collections.emptyList();
        }
        List<DetailItem> detailDataList = new ArrayList<>();
        detailDataList.add(new DetailItemHeader("Business Information"));
        // Phone Number
        boolean hasPhone = false;
        if (details.getFormattedPhoneNumber() != null) {
            detailDataList.add(new DetailItemHeaderIcon(details.getFormattedPhoneNumber(), R.drawable.ic_call));
            hasPhone = true;
        }
        // Handle Address
        boolean hasAddress = false;
        if (details.getFormattedAddress() != null) {
            if (hasPhone) {
                detailDataList.add(new DetailItemDivider(Constants.DIVIDER_LINE));
            }
            detailDataList.add(new DetailItemHeaderIcon(details.getFormattedAddress(), R.drawable.ic_location));
            hasAddress = true;
        }

        // Handle Hours
        if (details.getOpeningHours() != null) {
            // Handle Is Open
            if (details.getOpeningHours().getOpenNow() != null) {
                if (hasAddress) {
                    detailDataList.add(new DetailItemDivider(Constants.DIVIDER_LINE));
                }
                String openNow = details.getOpeningHours().getOpenNow() ? "Open Now" : "Closed";
                detailDataList.add(new DetailItemHeaderIcon("Hours: " + openNow, R.drawable.ic_time));
            }
            // Handle Hour List
            if (details.getOpeningHours().getWeekdayText() != null) {
                for (String day : details.getOpeningHours().getWeekdayText()) {
                    detailDataList.add(new DetailItemTime(day));
                }
                detailDataList.add(new DetailItemDivider(Constants.DIVIDER_SPACE));
            }
        }
        // Reviews
        if (details.getReviews() != null) {
            detailDataList.add(new DetailItemHeader("Reviews"));
            for (DetailsReview detailsReview : details.getReviews()) {
                detailDataList.add(detailsReview);
                detailDataList.add(new DetailItemDivider(Constants.DIVIDER_LINE));
            }
        }
        return detailDataList;
    }
}
