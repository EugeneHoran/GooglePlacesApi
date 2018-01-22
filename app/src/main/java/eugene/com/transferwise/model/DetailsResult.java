package eugene.com.transferwise.model;


import com.google.gson.annotations.SerializedName;

import eugene.com.transferwise.db.entity.Details;

public class DetailsResult {

    @SerializedName("result")
    private Details result;

    @SerializedName("status")
    private String status;

    public Details getResult() {
        return result;
    }

    public void setResult(Details result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
