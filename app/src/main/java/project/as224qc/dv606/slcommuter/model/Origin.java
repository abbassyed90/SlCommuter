package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class Origin implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Origin> CREATOR = new Parcelable.Creator<Origin>() {
        @Override
        public Origin createFromParcel(Parcel in) {
            return new Origin(in);
        }

        @Override
        public Origin[] newArray(int size) {
            return new Origin[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("time")
    private String time;
    @SerializedName("date")
    private String date;

    public Origin(String name, String time, String date) {
        this.name = name;
        this.time = time;
        this.date = date;
    }

    protected Origin(Parcel in) {
        name = in.readString();
        time = in.readString();
        date = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(date);
    }
}