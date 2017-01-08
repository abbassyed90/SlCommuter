package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class TripDTO extends Trip implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripDTO> CREATOR = new Parcelable.Creator<TripDTO>() {
        @Override
        public TripDTO createFromParcel(Parcel in) {
            return new TripDTO(in);
        }

        @Override
        public TripDTO[] newArray(int size) {
            return new TripDTO[size];
        }
    };
    @SerializedName("dur")
    private String duration;
    @SerializedName("chg")
    private String changes;
    @SerializedName("Leg")
    private ArrayList<Leg> legs;
    private String originTime;
    private String destinationTime;
    private CharSequence types;

    public TripDTO(String duration, String changes, ArrayList<Leg> legs) {
        this.duration = duration;
        this.changes = changes;
        this.legs = legs;
    }

    protected TripDTO(Parcel in) {
        duration = in.readString();
        changes = in.readString();
        if (in.readByte() == 0x01) {
            legs = new ArrayList<Leg>();
            in.readList(legs, Leg.class.getClassLoader());
        } else {
            legs = null;
        }
        originTime = in.readString();
        destinationTime = in.readString();
        types = (CharSequence) in.readValue(CharSequence.class.getClassLoader());
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public String getOriginTime() {
        return originTime;
    }

    public void setOriginTime(String originTime) {
        this.originTime = originTime;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public CharSequence getTypes() {
        return types;
    }

    public void setTypes(CharSequence types) {
        this.types = types;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(duration);
        dest.writeString(changes);
        if (legs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(legs);
        }
        dest.writeString(originTime);
        dest.writeString(destinationTime);
        dest.writeValue(types);
    }
}
