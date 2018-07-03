package opt.vacation.beans;

import java.time.Year;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

import opt.vacation.jpa.entities.DayMarker;
import opt.vacation.jpa.entities.LengthCombinationVariant;
import opt.vacation.jpa.entities.Period;
import opt.vacation.jpa.entities.PeriodCombinationVariant;

@SpringComponent
@VaadinSessionScope
public class DataBean {

	@NotNull
	private Integer year = Year.now().getValue();
	@Min(value=1) @Max(value=30) @NotNull
	private Integer days = 28;
	@Min(value=1) @Max(value=4) @NotNull
	private Integer parts = 2;
	private List<DayMarker> daysList = new LinkedList<>();
	private List<LengthCombinationVariant> combinationList = new LinkedList<>();
	private Map<Integer, List<Period>> periods = new LinkedHashMap<>();
	private Map<Double, List<PeriodCombinationVariant>> vacations = new LinkedHashMap<>();
	
	public DataBean() {
		super();
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getDays() {
		return days;
	}
	
	public void setDays(Integer days) {
		this.days = days;
	}
	
	public List<DayMarker> getDaysList() {
		return daysList;
	}

	public Integer getParts() {
		return parts;
	}

	public void setParts(Integer parts) {
		this.parts = parts;
	}

	public List<LengthCombinationVariant> getCombinationList() {
		return combinationList;
	}

	public Map<Integer, List<Period>> getPeriods() {
		return periods;
	}

	public Map<Double, List<PeriodCombinationVariant>> getVacations() {
		return vacations;
	}
	
}
