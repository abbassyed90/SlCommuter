package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class StationDTO implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StationDTO> CREATOR = new Parcelable.Creator<StationDTO>() {
        @Override
        public StationDTO createFromParcel(Parcel in) {
            return new StationDTO(in);
        }

        @Override
        public StationDTO[] newArray(int size) {
            return new StationDTO[size];
        }
    };
    @SerializedName("Name")
    private String name;
    @SerializedName("SiteId")
    private int siteId;

    public StationDTO() {
    }

    public StationDTO(String name, int siteId) {
        this.setName(name);
        this.setSiteId(siteId);
    }

    protected StationDTO(Parcel in) {
        name = in.readString();
        siteId = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(siteId);
    }

    public StationDTO copy() {
        StationDTO stationDTO = new StationDTO();

        stationDTO.setName(name);
        stationDTO.setSiteId(siteId);

        return stationDTO;
    }
}
