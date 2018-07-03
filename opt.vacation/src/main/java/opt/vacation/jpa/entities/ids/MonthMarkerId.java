package opt.vacation.jpa.entities.ids;

import java.io.Serializable;
import java.time.Month;

public class MonthMarkerId implements Serializable {

	private static final long serialVersionUID = -7104407424016317833L;
	
	private Integer yearMarker;
	private Month monthMarker;

	public MonthMarkerId() {
		this(null, null);
	}
	
	public MonthMarkerId(Integer yearMarker, Month monthMarker) {
		super();
		this.yearMarker = yearMarker;
		this.monthMarker = monthMarker;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MonthMarkerId other = (MonthMarkerId) obj;
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
