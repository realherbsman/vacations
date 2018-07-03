package opt.vacation.jpa.entities;

import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import opt.vacation.jpa.entities.converters.DoubleToBigDecimalAttributeConverter;
import opt.vacation.jpa.entities.ids.MonthMarkerId;

@Entity(name="MonthMarkerEntity")
@Table(name="MONTH_MARKERS")
@IdClass(value=MonthMarkerId.class)
public class MonthMarker {

	@Id
	@Column(name="YEAR_MARKER", nullable=false)
	private Integer yearMarker;
	
	@Id
	@Enumerated(value=EnumType.ORDINAL)
	@Column(name="MONTH_MARKER", nullable=false)
	private Month monthMarker;
	
	@Column(name="TOTAL_DAYS", nullable=false)
	private Integer totalDays;
	
	@Column(name="WORKDAYS", nullable=false)
	private Integer workdays;
	
	@Column(name="DAYS_OFF", nullable=false)
	private Integer daysOff;
	
	@Column(name="HOLIDAYS", nullable=false)
	private Integer holidays;
	
	@Column(name="PAYING_DAYS", nullable=false)
	private Integer payingDays;
	
	@Column(name="WORK_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double workPrice;
	
	@Column(name="VACATION_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double vacationPrice;

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

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public Integer getWorkdays() {
		return workdays;
	}

	public void setWorkdays(Integer workdays) {
		this.workdays = workdays;
	}

	public Integer getDaysOff() {
		return daysOff;
	}

	public void setDaysOff(Integer daysOff) {
		this.daysOff = daysOff;
	}

	public Integer getHolidays() {
		return holidays;
	}

	public void setHolidays(Integer holidays) {
		this.holidays = holidays;
	}

	public Integer getPayingDays() {
		return payingDays;
	}

	public void setPayingDays(Integer payingDays) {
		this.payingDays = payingDays;
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
	
}
