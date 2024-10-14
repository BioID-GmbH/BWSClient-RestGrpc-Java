package com.bws.restgrpcforwarder.datatypes;

/*
 * Re-implementation of the JSON data transfer objects as defined with the
 * BWS 3 protobuf messages and used by the RESTful JSON web API.
 * For a description of the elements please refer to the BWS 3 API reference
 * at https://developer.bioid.com/BWS/NewBws
 */

import java.util.List;
import org.springframework.lang.NonNull;
import java.util.ArrayList;

/*
 * @see https://developer.bioid.com/bws/grpc/photoverify
 */
public class PhotoVerifyRequestJson {

    private List<ImageDataJson> liveImages = new ArrayList<>();
    
    @NonNull
    private String photo;
    private boolean disableLivenessDetection;

    // Getters and setters
    public List<ImageDataJson> getLiveImages() {
        return liveImages;
    }

    public void setLiveImages(List<ImageDataJson> liveImages) {
        this.liveImages = liveImages;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        if (photo == null) {
            throw new IllegalArgumentException("photo is required");
        }
        this.photo = photo;
    }

    public boolean getDisableLivenessDetection() {
        return disableLivenessDetection;
    }

    public void setDisableLivenessDetection(boolean disableLivenessDetection) {
        this.disableLivenessDetection = disableLivenessDetection;
    }
}
