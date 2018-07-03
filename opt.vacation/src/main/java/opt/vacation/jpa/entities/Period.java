package opt.vacation.jpa.entities;

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

import opt.vacation.jpa.entities.converters.DoubleToBigDecimalAttributeConverter;
import opt.vacation.jpa.entities.converters.LocalDateToDateAttributeConverter;
import opt.vacation.jpa.entities.ids.PeriodId;

@Entity(name="PeriodEntity")
@Table(
		name="PERIODS", 
		indexes={
				@Index(name="PERIOD_YEAR_IDX", columnList="FIRST_DAY, OFFICIAL_LENGTH, YEAR_MARKER", unique=true)})
@IdClass(value=PeriodId.class)
public class Period implements Comparable<Period> {

	@Id
	@Column(name="YEAR_MARKER", nullable=false)
	private Integer yearMarker;
	@Id
	@Column(name="FIRST_DATE_YEAR", nullable=false)
	private Integer firstDateYear;	
	@Id
	@Enumerated(value=EnumType.ORDINAL)
	@Column(name="FIRST_DATE_MONTH", nullable=false)
	private Month firstDateMonth;
	@Id
	@Column(name="FIRST_DATE_DAY", nullable=false)
	private Integer firstDateDay;	
	@Id
	@Column(name="OFFICIAL_LENGTH", nullable=false)
	private Integer officialLength;

	@Column(name="FIRST_DAY", nullable=false)
	@Convert(converter=LocalDateToDateAttributeConverter.class)
	private LocalDate firstDay;
	@Column(name="LAST_DAY", nullable=false)
	@Convert(converter=LocalDateToDateAttributeConverter.class)
	private LocalDate lastDay;
	@Column(name="ACTUAL_LENGTH", nullable=false)
	private Integer actualLength;
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
	public Integer getOfficialLength() {
		return officialLength;
	}
	public void setOfficialLength(Integer officialLength) {
		this.officialLength = officialLength;
	}
	public LocalDate getFirstDay() {
		return firstDay;
	}
	public void setFirstDay(LocalDate firstDay) {
		this.firstDay = firstDay;
	}
	public LocalDate getLastDay() {
		return lastDay;
	}
	public void setLastDay(LocalDate lastDay) {
		this.lastDay = lastDay;
	}
	public Integer getActualLength() {
		return actualLength;
	}
	public void setActualLength(Integer actualLength) {
		this.actualLength = actualLength;
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
	public Integer getFirstDateYear() {
		return firstDateYear;
	}
	public void setFirstDateYear(Integer firstDateYear) {
		this.firstDateYear = firstDateYear;
	}
	public Month getFirstDateMonth() {
		return firstDateMonth;
	}
	public void setFirstDateMonth(Month firstDateMonth) {
		this.firstDateMonth = firstDateMonth;
	}
	public Integer getFirstDateDay() {
		return firstDateDay;
	}
	public void setFirstDateDay(Integer firstDateDay) {
		this.firstDateDay = firstDateDay;
	}
	@Override
	public int compareTo(Period other) {
		int result = this.firstDay.compareTo(other.firstDay);
		if (result == 0) {
			result = this.officialLength.compareTo(other.officialLength);
			if (result == 0) {
				return this.yearMarker.compareTo(other.yearMarker);
			}
		} 
		return result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstDay == null) ? 0 : firstDay.hashCode());
		result = prime * result + ((officialLength == null) ? 0 : officialLength.hashCode());
		result = prime * result + ((yearMarker == null) ? 0 : yearMarker.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Period other = (Period) obj;
		if (firstDay == null) {
			if (other.firstDay != null)
				return false;
		} else if (!firstDay.equals(other.firstDay))
			return false;
		if (officialLength == null) {
			if (other.officialLength != null)
				return false;
		} else if (!officialLength.equals(other.officialLength))
			return false;
		if (yearMarker == null) {
			if (other.yearMarker != null)
				return false;
		} else if (!yearMarker.equals(other.yearMarker))
			return false;
		return true;
	}
		
}
