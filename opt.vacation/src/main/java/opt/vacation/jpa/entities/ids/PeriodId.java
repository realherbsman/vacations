package opt.vacation.jpa.entities.ids;

import java.io.Serializable;
import java.time.Month;

public class PeriodId implements Serializable {

	private static final long serialVersionUID = -3442166642239733226L;
	private Integer yearMarker;
	private Integer firstDateYear;
	private Month firstDateMonth;
	private Integer firstDateDay;	
	private Integer officialLength;
	public Integer getYearMarker() {
		return yearMarker;
	}
	public void setYearMarker(Integer yearMarker) {
		this.yearMarker = yearMarker;
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
	public Integer getOfficialLength() {
		return officialLength;
	}
	public void setOfficialLength(Integer officialLength) {
		this.officialLength = officialLength;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstDateDay == null) ? 0 : firstDateDay.hashCode());
		result = prime * result + ((firstDateMonth == null) ? 0 : firstDateMonth.hashCode());
		result = prime * result + ((firstDateYear == null) ? 0 : firstDateYear.hashCode());
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
		PeriodId other = (PeriodId) obj;
		if (firstDateDay == null) {
			if (other.firstDateDay != null)
				return false;
		} else if (!firstDateDay.equals(other.firstDateDay))
			return false;
		if (firstDateMonth != other.firstDateMonth)
			return false;
		if (firstDateYear == null) {
			if (other.firstDateYear != null)
				return false;
		} else if (!firstDateYear.equals(other.firstDateYear))
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
