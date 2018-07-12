package residentevil.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CapitalDto {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;

    public CapitalDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
