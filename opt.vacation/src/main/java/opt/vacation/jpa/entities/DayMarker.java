package opt.vacation.jpa.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import opt.vacation.jpa.entities.converters.BooleanToIntegerAttributeConverter;
import opt.vacation.jpa.entities.converters.DoubleToBigDecimalAttributeConverter;
import opt.vacation.jpa.entities.converters.LocalDateToDateAttributeConverter;
import opt.vacation.jpa.entities.ids.DayMarkerId;

@Entity(name="DayMarkerEntity")
@Table(
		name="DAY_MARKERS",
		indexes={@Index(name="DAY_MARKER_IDX", columnList="DATE_MARKER", unique=true)})
@IdClass(value=DayMarkerId.class)
public class DayMarker {

	@Id
	@Column(name="YEAR_MARKER", nullable=false)
	private Integer yearMarker;
	
	@Id
	@Enumerated(value=EnumType.ORDINAL)
	@Column(name="MONTH_MARKER", nullable=false)
	private Month monthMarker;
	
	@Id
	@Column(name="DAY_MARKER", nullable=false)
	private Integer dayMarker;
	
	@Column(name="DATE_MARKER", nullable=false)
	@Convert(converter=LocalDateToDateAttributeConverter.class)
	private LocalDate dateMarker;	
	
	@Enumerated(value=EnumType.ORDINAL)
	@Column(name="DAY_OF_WEEK", nullable=false)
	private DayOfWeek dayOfWeek;
	
	@Column(name="IS_HOLIDAY", nullable=false)
	@Convert(converter=BooleanToIntegerAttributeConverter.class)
	private Boolean isHoliday;

	@Column(name="WORK_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double workPrice;

	@Column(name="VACATION_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double vacationPrice;

	public LocalDate getDateMarker() {
		return dateMarker;
	}

	public void setDateMarker(LocalDate dateMarker) {
		this.dateMarker = dateMarker;
	}

	public Integer getYearMarker() {
		return yearMarker;
	}

	public void setYearMarker(Integer yearMarker) {
		this.yearMarker = yearMarker;
	}

	public Month getMonthMarker() {
		return monthMarker;
	}

	public void setMonthMarker(Month monthMarker) {
		this.monthMarker = monthMarker;
	}

	public Integer getDayMarker() {
		return dayMarker;
	}

	public void setDayMarker(Integer dayMarker) {
		this.dayMarker = dayMarker;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Boolean getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

	public Double getWorkPrice() {
		return workPrice;
	}

	public void setWorkPrice(Double workPrice) {
		this.workPrice = workPrice;
	}

	public Double getVacationPrice() {
		return vacationPrice;
	}

	public void setVacationPrice(Double vacationPrice) {
		this.vacationPrice = vacationPrice;
	}

	@Override
	public String toString() {
		return "DayMarker [yearMarker=" + yearMarker + ", monthMarker=" + monthMarker + ", dayMarker=" + dayMarker
				+ ", dateMarker=" + dateMarker + ", dayOfWeek=" + dayOfWeek + ", isHoliday=" + isHoliday + ", dayPrice="
				+ workPrice + ", vacationPrice=" + vacationPrice + "]";
	}
	
}
