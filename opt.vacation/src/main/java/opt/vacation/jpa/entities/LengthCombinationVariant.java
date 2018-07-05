package opt.vacation.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import opt.vacation.jpa.entities.ids.LengthCombinationVariantId;

@Entity(name="LengthCombinationVariantEntity")
@Table(name="LC_VARIANTS")
@IdClass(value=LengthCombinationVariantId.class)
public class LengthCombinationVariant {

	@Id
	@Column(name="LC_SUM", nullable=false)
	private Integer sum;
	@Id
	@Column(name="LC_PARTS", nullable=false)
	private Integer parts;
	@Id
	@Column(name="LC_VARIANT_ID", nullable=false)
	private Integer variantId;
	@Column(name="MAX_ELEMENT", nullable=false)
	private Integer max;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(
			name="LC_VARIANT_ELEMENTS",
			indexes={
					@Index(
							name="LC_VARIANT_ELEMENTS_IDX", 
							columnList="LC_SUM, LC_PARTS, LC_VARIANT_ID", 
							unique=false)},
			joinColumns={
					@JoinColumn(name="LC_SUM", referencedColumnName="LC_SUM"), 
					@JoinColumn(name="LC_PARTS", referencedColumnName="LC_PARTS"), 
					@JoinColumn(name="LC_VARIANT_ID", referencedColumnName="LC_VARIANT_ID")})
	@Column(name="ELEMENT", nullable=false)
	@OrderColumn(name="ELEMENT_ID")
	private List<Integer> elements;
	
	public LengthCombinationVariant() {
		super();
	}

	public LengthCombinationVariant(Integer element) {
		super();
		variantId = 0;
		parts = 1;
		elements = new ArrayList<>(parts);
		elements.add(element);
		max = element;
		sum = element;
	}	
	
	public LengthCombinationVariant(Integer element, LengthCombinationVariant prototype) {
		super();
		variantId = 0;
		parts = prototype.parts + 1;
		elements = new ArrayList<>(prototype.elements);
		elements.add(element);
		max = prototype.max < element ? element : prototype.max;
		sum = prototype.sum + element;
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

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public List<Integer> getElements() {
		return elements;
	}

	public void setElements(List<Integer> elements) {
		this.elements = elements;
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
		LengthCombinationVariant other = (LengthCombinationVariant) obj;
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
