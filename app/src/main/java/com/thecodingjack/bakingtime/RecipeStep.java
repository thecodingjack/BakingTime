package com.thecodingjack.bakingtime;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeStep {
    private String shortDescription;
    private String fullDescription;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStep(String shortDescription, String fullDescription, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}


