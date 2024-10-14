package com.bws.restgrpcforwarder.datatypes;

/*
 * Re-implementation of the JSON data transfer objects as defined with the
 * BWS 3 protobuf messages and used by the RESTful JSON web API.
 * For a description of the elements please refer to the BWS 3 API reference
 * at https://developer.bioid.com/BWS/NewBws
 */

import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;

/*
 * @see https://developer.bioid.com/bws/grpc/ImageData
 *
 * 
 */
public class ImageDataJson {

    @NonNull
    private String image;
    private List<String> tags = new ArrayList<>();

    // Getters and setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null) {
            throw new IllegalArgumentException("image is required");
        }
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
