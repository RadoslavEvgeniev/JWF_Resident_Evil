package residentevil.dtos;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import residentevil.common.annotations.DateBeforeToday;
import residentevil.entities.enums.Magnitude;
import residentevil.entities.enums.Mutation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class VirusDto {

    private String id;
    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private Set<CapitalDto> capitals;

    private List<Long> capitalIds;

    public VirusDto() {

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "Name cannot be null.")
    @Length(min = 3, max = 10, message = "Invalid name.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Description cannot be null.")
    @Length(min = 5, max = 100, message = "Invalid description.")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Side effects cannot be null.")
    @Length(max = 50, message = "Invalid side effects.")
    public String getSideEffects() {
        return this.sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @NotNull(message = "Creator cannot be null.")
    @Pattern(regexp = "^[Cc]orp$", message = "Invalid creator.")
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean getDeadly() {
        return this.isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean getCurable() {
        return this.isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = "Mutation cannot be null.")
    public Mutation getMutation() {
        return this.mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @NotNull(message = "Turnover rate cannot be null.")
    @Min(value = 0, message = "Invalid turnover rate")
    @Max(value = 100, message = "Invalid turnover rate")
    public Integer getTurnoverRate() {
        return this.turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @NotNull(message = "Hours until turn cannot be null.")
    @Min(value = 1, message = "Invalid hours until turn")
    @Max(value = 12, message = "Invalid hours until turn")
    public Integer getHoursUntilTurn() {
        return this.hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = "Magnitude cannot be null.")
    public Magnitude getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @DateBeforeToday(message = "Invalid Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return this.releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    public Set<CapitalDto> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(Set<CapitalDto> capitals) {
        this.capitals = capitals;
    }

    @NotNull(message = "You must select capitals")
    @Size(min = 1, message = "You must select capitals")
    public List<Long> getCapitalIds() {
        return this.capitalIds;
    }

    public void setCapitalIds(List<Long> capitalIds) {
        this.capitalIds = capitalIds;
    }
}
