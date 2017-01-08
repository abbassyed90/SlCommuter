package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class DeviationDTO implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DeviationDTO> CREATOR = new Parcelable.Creator<DeviationDTO>() {
        @Override
        public DeviationDTO createFromParcel(Parcel in) {
            return new DeviationDTO(in);
        }

        @Override
        public DeviationDTO[] newArray(int size) {
            return new DeviationDTO[size];
        }
    };
    @SerializedName("DevCaseGid")
    private long id;
    private long created;
    @SerializedName("Details")
    private String details;
    @SerializedName("Header")
    private String header;
    @SerializedName("Scope")
    private String scope;
    private long fromDate;
    private long toDate;


    public DeviationDTO() {
    }

    public DeviationDTO(long created, String details, String header, String scope, long fromDate, long toDate) {
        this.created = created;
        this.details = details;
        this.header = header;
        this.scope = scope;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    protected DeviationDTO(Parcel in) {
        id = in.readLong();
        created = in.readLong();
        details = in.readString();
        header = in.readString();
        scope = in.readString();
        fromDate = in.readLong();
        toDate = in.readLong();
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(created);
        dest.writeString(details);
        dest.writeString(header);
        dest.writeString(scope);
        dest.writeLong(fromDate);
        dest.writeLong(toDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}