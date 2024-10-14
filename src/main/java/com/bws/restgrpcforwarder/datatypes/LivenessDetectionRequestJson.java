package com.bws.restgrpcforwarder.datatypes;

/*
 * Re-implementation of the JSON data transfer objects as defined with the
 * BWS 3 protobuf messages and used by the RESTful JSON web API.
 * For a description of the elements please refer to the BWS 3 API reference
 * at https://developer.bioid.com/BWS/NewBws
 */

import java.util.List;
import java.util.ArrayList;


/*
 * @see https://developer.bioid.com/bws/grpc/livenessdetection
 */
public class LivenessDetectionRequestJson {

    private List<ImageDataJson> liveImages = new ArrayList<>();

    public List<ImageDataJson> getLiveImages() {
        return liveImages;
    }

    public void setLiveImages(List<ImageDataJson> liveImages) {
        this.liveImages = liveImages;
    }
}



