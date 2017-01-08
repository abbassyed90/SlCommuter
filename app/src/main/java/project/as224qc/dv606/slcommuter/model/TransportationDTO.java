package project.as224qc.dv606.slcommuter.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class TransportationDTO {
    @SerializedName("DisplayTime")
    private String displayTime;

    @SerializedName("TransportMode")
    private String transportMode;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("PlatformMessage")
    private String platformMessage;

    @SerializedName("LineNumber")
    private String lineNumber;

    private long timeTabledDate;

    private long expectedTimeDate;

    public TransportationDTO(String displayTime, String transportMode, String destination, String platformMessage, String lineNumber, long timeTabledDate, long expectedTimeDate) {
        this.displayTime = displayTime;
        this.transportMode = transportMode;
        this.destination = destination;
        this.platformMessage = platformMessage;
        this.lineNumber = lineNumber;
        this.timeTabledDate = timeTabledDate;
        this.expectedTimeDate = expectedTimeDate;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPlatformMessage() {
        return platformMessage;
    }

    public void setPlatformMessage(String platformMessage) {
        this.platformMessage = platformMessage;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public long getTimeTabledDate() {
        return timeTabledDate;
    }

    public void setTimeTabledDate(long timeTabledDate) {
        this.timeTabledDate = timeTabledDate;
    }

    public long getExpectedTimeDate() {
        return expectedTimeDate;
    }

    public void setExpectedTimeDate(long expectedTimeDate) {
        this.expectedTimeDate = expectedTimeDate;
    }
}
