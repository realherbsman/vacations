package opt.vacation.jpa.entities.ids;

import java.io.Serializable;
import java.time.Month;

public class DayMarkerId implements Serializable {

	private static final long serialVersionUID = 6624096081012717927L;

	private Integer yearMarker;
	private Month monthMarker;
	private Integer dayMarker;

	public DayMarkerId() {
		this(null, null, null);
	}

	public DayMarkerId(Integer yearMarker, Month monthMarker, Integer dayMarker) {
		super();
		this.yearMarker = yearMarker;
		this.monthMarker = monthMarker;
		this.dayMarker = dayMarker;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayMarker == null) ? 0 : dayMarker.hashCode());
		result = prime * result + ((monthMarker == null) ? 0 : monthMarker.hashCode());
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
		DayMarkerId other = (DayMarkerId) obj;
		if (dayMarker == null) {
			if (other.dayMarker != null)
				return false;
		} else if (!dayMarker.equals(other.dayMarker))
			return false;
		if (monthMarker != other.monthMarker)
			return false;
		if (yearMarker == null) {
			if (other.yearMarker != null)
				return false;
		} else if (!yearMarker.equals(other.yearMarker))
			return false;
		return true;
	}
		
}
