package eugene.com.transferwise.model;


import java.util.List;

import eugene.com.livelibrary.Resource;
import eugene.com.transferwise.db.entity.Details;

public class PlaceDetailsData {
    private Resource<Details> detailsResource;
    private List<DetailItem> detailsList;

    public PlaceDetailsData(Resource<Details> detailsResource, List<DetailItem> detailsList) {
        this.detailsResource = detailsResource;
        this.detailsList = detailsList;
    }

    public Resource<Details> getDetailsResource() {
        return detailsResource;
    }

    public List<DetailItem> getDetailsList() {
        return detailsList;
    }
}
