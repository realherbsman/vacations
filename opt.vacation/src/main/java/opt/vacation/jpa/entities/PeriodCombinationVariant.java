package opt.vacation.jpa.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import opt.vacation.jpa.entities.converters.DoubleToBigDecimalAttributeConverter;
import opt.vacation.jpa.entities.converters.UUIDToStringAttributeConverter;

@Entity(name="PeriodCombinationVariantEntity")
@Table(
		name="PERIOD_COMBINATION_VARIANTS",
		indexes={@Index(name="PC_YEAR_IDX", columnList="YEAR_MARKER", unique=false)})
public class PeriodCombinationVariant implements Comparable<PeriodCombinationVariant> {

	@Id
	@Column(name="VARIANT_ID", nullable=false, length=36)
	@Convert(converter=UUIDToStringAttributeConverter.class)
	private UUID variantId;
	
	@Column(name="YEAR_MARKER")
	private Integer yearMarker;
	
	@Column(name="WORK_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double workPrice;
	@Column(name="VACATION_PRICE", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double vacationPrice;
	@Column(name="RATING_DELTA", nullable=false, precision=18, scale=15)
	@Convert(converter=DoubleToBigDecimalAttributeConverter.class)
	private Double ratingDelta;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
			name="PERIOD_COMBINATION_VARIANT_LC",
			joinColumns={@JoinColumn(name="VARIANT_ID")},
			inverseJoinColumns={
					@JoinColumn(name="LC_SUM", referencedColumnName="LC_SUM"),
					@JoinColumn(name="LC_PARTS", referencedColumnName="LC_PARTS"),
					@JoinColumn(name="LC_VARIANT_ID", referencedColumnName="LC_VARIANT_ID")})
	private LengthCombinationVariant lengthCombination;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="PERIOD_COMBINATION_VARIANT_ELEMENTS",	
			joinColumns={@JoinColumn(name="VARIANT_ID")},
			inverseJoinColumns={
					@JoinColumn(name="YEAR_MARKER", referencedColumnName="YEAR_MARKER"),
					@JoinColumn(name="FIRST_DATE_YEAR", referencedColumnName="FIRST_DATE_YEAR"),
					@JoinColumn(name="FIRST_DATE_MONTH", referencedColumnName="FIRST_DATE_MONTH"),
					@JoinColumn(name="FIRST_DATE_DAY", referencedColumnName="FIRST_DATE_DAY"),
					@JoinColumn(name="OFFICIAL_LENGTH", referencedColumnName="OFFICIAL_LENGTH")})
	@OrderColumn(name="PERIOD_ID")
	private List<Period> periods;

	public PeriodCombinationVariant() {
		super();
	}
	
	public PeriodCombinationVariant(Period period, int year, LengthCombinationVariant lengthCombination) {
		this();
		this.variantId = UUID.randomUUID();
		this.yearMarker = year;
		this.lengthCombination = lengthCombination;
		this.periods = new LinkedList<>();
		this.periods.add(period);
		this.workPrice = period.getWorkPrice();
		this.vacationPrice = period.getVacationPrice();
		this.ratingDelta = this.vacationPrice - this.workPrice;
	}
	
	public PeriodCombinationVariant(Period period, PeriodCombinationVariant prototype) {
		this();
		this.variantId = UUID.randomUUID();
		this.yearMarker = prototype.yearMarker;
		this.lengthCombination = prototype.lengthCombination;
		this.periods = new LinkedList<>(prototype.periods);
		this.periods.add(period);
		Collections.sort(this.periods);
		this.workPrice = prototype.workPrice + period.getWorkPrice();
		this.vacationPrice = prototype.vacationPrice + period.getVacationPrice();
		this.ratingDelta = this.vacationPrice - this.workPrice;
	}
	
	public UUID getVariantId() {
		return variantId;
	}

	public void setVariantId(UUID variantId) {
		this.variantId = variantId;
	}

	public Integer getYearMarker() {
		return yearMarker;
	}

	public void setYearMarker(Integer yearMarker) {
		this.yearMarker = yearMarker;
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

	public Double getRatingDelta() {
		return ratingDelta;
	}

	public void setRatingDelta(Double ratingDelta) {
		this.ratingDelta = ratingDelta;
	}

	public LengthCombinationVariant getLengthCombination() {
		return lengthCombination;
	}

	public void setLengthCombination(LengthCombinationVariant lengthCombination) {
		this.lengthCombination = lengthCombination;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	@Override
	public int compareTo(PeriodCombinationVariant other) {
		int result = this.getYearMarker().compareTo(other.getYearMarker());
		if (result == 0) {
			result = this.lengthCombination.getSum().compareTo(other.getLengthCombination().getSum());
			if (result == 0) {
				result = this.getRatingDelta().compareTo(other.getRatingDelta());
				if (result == 0) {
					return this.getVariantId().compareTo(other.getVariantId());
				}
			}
		} 
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lengthCombination == null) ? 0 : lengthCombination.hashCode());
		result = prime * result + ((periods == null) ? 0 : periods.hashCode());
		result = prime * result + ((ratingDelta == null) ? 0 : ratingDelta.hashCode());
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
		PeriodCombinationVariant other = (PeriodCombinationVariant) obj;
		if (lengthCombination == null) {
			if (other.lengthCombination != null)
				return false;
		} else if (!lengthCombination.equals(other.lengthCombination))
			return false;
		if (periods == null) {
			if (other.periods != null)
				return false;
		} else if (!periods.equals(other.periods))
			return false;
		if (ratingDelta == null) {
			if (other.ratingDelta != null)
				return false;
		} else if (!ratingDelta.equals(other.ratingDelta))
			return false;
		if (yearMarker == null) {
			if (other.yearMarker != null)
				return false;
		} else if (!yearMarker.equals(other.yearMarker))
			return false;
		return true;
	}

}
