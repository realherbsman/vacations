package opt.vacation.jpa.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import opt.vacation.jpa.entities.converters.DoubleToBigDecimalAttributeConverter;
import opt.vacation.jpa.entities.ids.PeriodCombinationVariantId;

@Entity(name="PeriodCombinationVariantEntity")
@Table(name="PC_VARIANTS")
@IdClass(value=PeriodCombinationVariantId.class)
public class PeriodCombinationVariant {

	@Id
	@Column(name="YEAR_MARKER")
	private Integer yearMarker;
	@Id
	@Column(name="LC_SUM")
	private Integer lcSum;
	@Id
	@Column(name="LC_PARTS")
	private Integer lcParts;
	@Id
	@Column(name="LC_VARIANT_ID")
	private Integer lcVariantId;
	@Id
	@Column(name="VARIANT_ID")
	private Integer variantId;
	
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
	@JoinColumns(value={
					@JoinColumn(name="LC_SUM", referencedColumnName="LC_SUM", insertable=false, updatable=false),
					@JoinColumn(name="LC_PARTS", referencedColumnName="LC_PARTS", insertable=false, updatable=false),
					@JoinColumn(name="LC_VARIANT_ID", referencedColumnName="LC_VARIANT_ID", insertable=false, updatable=false)})
	private LengthCombinationVariant lengthCombination;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="PC_VARIANT_ELEMENTS",	
			joinColumns={
					@JoinColumn(name="YEAR_MARKER_VARIANT", referencedColumnName="YEAR_MARKER", insertable=false, updatable=false),
					@JoinColumn(name="LC_SUM", referencedColumnName="LC_SUM", insertable=false, updatable=false),
					@JoinColumn(name="LC_PARTS", referencedColumnName="LC_PARTS", insertable=false, updatable=false),
					@JoinColumn(name="LC_VARIANT_ID", referencedColumnName="LC_VARIANT_ID", insertable=false, updatable=false),
					@JoinColumn(name="VARIANT_ID", referencedColumnName="VARIANT_ID", insertable=false, updatable=false)},
			inverseJoinColumns={
					@JoinColumn(name="YEAR_MARKER_PERIOD", referencedColumnName="YEAR_MARKER", insertable=false, updatable=false),
					@JoinColumn(name="FIRST_DATE_YEAR", referencedColumnName="FIRST_DATE_YEAR", insertable=false, updatable=false),
					@JoinColumn(name="FIRST_DATE_MONTH", referencedColumnName="FIRST_DATE_MONTH", insertable=false, updatable=false),
					@JoinColumn(name="FIRST_DATE_DAY", referencedColumnName="FIRST_DATE_DAY", insertable=false, updatable=false),
					@JoinColumn(name="OFFICIAL_LENGTH", referencedColumnName="OFFICIAL_LENGTH", insertable=false, updatable=false)})
	@OrderColumn(name="PERIOD_ID")
	private List<Period> periods;

	public PeriodCombinationVariant() {
		super();
	}
	
	public PeriodCombinationVariant(Period period, int year, LengthCombinationVariant lengthCombination) {
		this();
		this.yearMarker = year;
		this.variantId = 0;
		this.lcSum = lengthCombination.getSum();
		this.lcParts = lengthCombination.getParts();
		this.lcVariantId = lengthCombination.getVariantId();
		this.lengthCombination = lengthCombination;
		this.periods = new LinkedList<>();
		this.periods.add(period);
		this.workPrice = period.getWorkPrice();
		this.vacationPrice = period.getVacationPrice();
		this.ratingDelta = this.vacationPrice - this.workPrice;
	}
	
	public PeriodCombinationVariant(Period period, PeriodCombinationVariant prototype) {
		this();
		this.yearMarker = prototype.yearMarker;
		this.variantId = 0;
		this.lcSum = prototype.getLcSum();
		this.lcParts = prototype.getLcParts();
		this.lcVariantId = prototype.getLcVariantId();
		this.lengthCombination = prototype.lengthCombination;
		this.periods = new LinkedList<>(prototype.periods);
		this.periods.add(period);
		Collections.sort(this.periods);
		this.workPrice = prototype.workPrice + period.getWorkPrice();
		this.vacationPrice = prototype.vacationPrice + period.getVacationPrice();
		this.ratingDelta = this.vacationPrice - this.workPrice;
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

}
