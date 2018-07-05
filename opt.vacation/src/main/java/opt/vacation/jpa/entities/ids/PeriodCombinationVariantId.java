package opt.vacation.jpa.entities.ids;

import java.io.Serializable;

public class PeriodCombinationVariantId implements Serializable {

	private static final long serialVersionUID = -6222260814564553076L;
	
	private Integer yearMarker;
	private Integer lcSum;
	private Integer lcParts;
	private Integer lcVariantId;
	private Integer variantId;
	
	public Integer getYearMarker() {
		return yearMarker;
	}
	public void setYearMarker(Integer yearMarker) {
		this.yearMarker = yearMarker;
	}
	public Integer getLcSum() {
		return lcSum;
	}
	public void setLcSum(Integer lcSum) {
		this.lcSum = lcSum;
	}
	public Integer getLcParts() {
		return lcParts;
	}
	public void setLcParts(Integer lcParts) {
		this.lcParts = lcParts;
	}
	public Integer getLcVariantId() {
		return lcVariantId;
	}
	public void setLcVariantId(Integer lcVariantId) {
		this.lcVariantId = lcVariantId;
	}
	public Integer getVariantId() {
		return variantId;
	}
	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lcParts == null) ? 0 : lcParts.hashCode());
		result = prime * result + ((lcSum == null) ? 0 : lcSum.hashCode());
		result = prime * result + ((lcVariantId == null) ? 0 : lcVariantId.hashCode());
		result = prime * result + ((variantId == null) ? 0 : variantId.hashCode());
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
		PeriodCombinationVariantId other = (PeriodCombinationVariantId) obj;
		if (lcParts == null) {
			if (other.lcParts != null)
				return false;
		} else if (!lcParts.equals(other.lcParts))
			return false;
		if (lcSum == null) {
			if (other.lcSum != null)
				return false;
		} else if (!lcSum.equals(other.lcSum))
			return false;
		if (lcVariantId == null) {
			if (other.lcVariantId != null)
				return false;
		} else if (!lcVariantId.equals(other.lcVariantId))
			return false;
		if (variantId == null) {
			if (other.variantId != null)
				return false;
		} else if (!variantId.equals(other.variantId))
			return false;
		if (yearMarker == null) {
			if (other.yearMarker != null)
				return false;
		} else if (!yearMarker.equals(other.yearMarker))
			return false;
		return true;
	}
	
}
