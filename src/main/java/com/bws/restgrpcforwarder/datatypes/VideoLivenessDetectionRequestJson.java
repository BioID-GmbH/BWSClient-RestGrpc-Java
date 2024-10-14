package com.bws.restgrpcforwarder.datatypes;

/*
 * Re-implementation of the JSON data transfer objects as defined with the
 * BWS 3 protobuf messages and used by the RESTful JSON web API.
 * For a description of the elements please refer to the BWS 3 API reference
 * at https://developer.bioid.com/BWS/NewBws
 */

/*
 * @see https://developer.bioid.com/bws/grpc/videolivenessdetection
 */
public class VideoLivenessDetectionRequestJson {

    private String video = "";

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
