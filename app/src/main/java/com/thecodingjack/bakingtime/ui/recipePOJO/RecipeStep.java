package com.thecodingjack.bakingtime.ui.recipePOJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lamkeong on 7/7/2017.
 */

public class RecipeStep implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shortDescription);
        dest.writeString(this.fullDescription);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    protected RecipeStep(Parcel in) {
        this.shortDescription = in.readString();
        this.fullDescription = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };
}


