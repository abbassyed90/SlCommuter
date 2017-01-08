package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class Leg implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Leg> CREATOR = new Parcelable.Creator<Leg>() {
        @Override
        public Leg createFromParcel(Parcel in) {
            return new Leg(in);
        }

        @Override
        public Leg[] newArray(int size) {
            return new Leg[size];
        }
    };
    @SerializedName("idx")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("line")
    private String line;
    @SerializedName("dir")
    private String direction;
    @SerializedName("Origin")
    private Origin origin;
    @SerializedName("Destination")
    private Destination destination;
    @SerializedName("dist")
    private String distance;

    public Leg(String id, String name, String type, String line, String direction, Origin origin, Destination destination) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.line = line;
        this.direction = direction;
        this.origin = origin;
        this.destination = destination;
    }

    protected Leg(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        line = in.readString();
        direction = in.readString();
        origin = (Origin) in.readValue(Origin.class.getClassLoader());
        destination = (Destination) in.readValue(Destination.class.getClassLoader());
        distance = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(line);
        dest.writeString(direction);
        dest.writeValue(origin);
        dest.writeValue(destination);
        dest.writeString(distance);
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}