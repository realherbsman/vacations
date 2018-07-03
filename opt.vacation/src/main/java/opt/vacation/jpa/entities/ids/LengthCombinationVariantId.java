package opt.vacation.jpa.entities.ids;

import java.io.Serializable;

public class LengthCombinationVariantId implements Serializable {

	private static final long serialVersionUID = 7204203280317363918L;

	private Integer sum;
	private Integer parts;
	private Integer variantId;
	
	public LengthCombinationVariantId() {
		this(null, null, null);
	}
	
	public LengthCombinationVariantId(Integer sum, Integer parts, Integer variantId) {
		super();
		this.sum = sum;
		this.parts = parts;
		this.variantId = variantId;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getParts() {
		return parts;
	}

	public void setParts(Integer parts) {
		this.parts = parts;
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
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		result = prime * result + ((variantId == null) ? 0 : variantId.hashCode());
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
		LengthCombinationVariantId other = (LengthCombinationVariantId) obj;
		if (parts == null) {
			if (other.parts != null)
				return false;
		} else if (!parts.equals(other.parts))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		if (variantId == null) {
			if (other.variantId != null)
				return false;
		} else if (!variantId.equals(other.variantId))
			return false;
		return true;
	}

}
