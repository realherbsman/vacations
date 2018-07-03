package opt.vacation.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="YearMarkerEntity")
@Table(name="YEAR_MARKERS")
public class YearMarker {

	@Id
	@Column(name="YEAR_MARKER", nullable=false)
	private Integer yearMarker;
	
	@Column(name="TOTAL_DAYS", nullable=false)
	private Integer totalDays;

	public Integer getYearMarker() {
		return yearMarker;
	}

	public void setYearMarker(Integer yearMarker) {
		this.yearMarker = yearMarker;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

}
