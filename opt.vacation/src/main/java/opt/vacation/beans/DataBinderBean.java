package opt.vacation.beans;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.UI;

import opt.vacation.jpa.entities.DayMarker;
import opt.vacation.jpa.entities.LengthCombinationVariant;
import opt.vacation.jpa.entities.Period;
import opt.vacation.jpa.entities.PeriodCombinationVariant;
import opt.vacation.ui.components.ProgressPlaceholder;

@SpringComponent
@VaadinSessionScope
public class DataBinderBean {

	@Autowired
	private DataBean data;
	
	private Binder<DataBean> dataBinder;
	private ListDataProvider<DayMarker> dayMarkerDataProvider;
	private ListDataProvider<LengthCombinationVariant> combinationDataProvider;
	private ListDataProvider<Entry<Integer, List<Period>>> periodDataProvider;
	private ListDataProvider<Entry<Double, List<PeriodCombinationVariant>>> vacationDataProvider;
	
	public DataBinderBean() {
		super();
	}
	
	@PostConstruct
	protected void init() {
		this.dataBinder = new Binder<>();
		this.dataBinder.setBean(data);
		this.dayMarkerDataProvider = DataProvider.ofCollection(data.getDaysList());
		this.combinationDataProvider = DataProvider.ofCollection(data.getCombinationList());
		this.periodDataProvider = DataProvider.ofCollection(data.getPeriods().entrySet());
		this.vacationDataProvider = DataProvider.ofCollection(data.getVacations().entrySet());
	}

	public DataBean getData() {
		return data;
	}	
	
	public Binder<DataBean> getDataBinder() {
		return dataBinder;
	}

	public ListDataProvider<DayMarker> getDayMarkerDataProvider() {
		return dayMarkerDataProvider;
	}

	public ListDataProvider<Entry<Integer, List<Period>>> getPeriodDataProvider() {
		return periodDataProvider;
	}
	
	public ListDataProvider<LengthCombinationVariant> getLengthCombinationDataProvider() {
		return combinationDataProvider;
	}
	
	public ListDataProvider<Entry<Double, List<PeriodCombinationVariant>>> getVacationDataProvider() {
		return vacationDataProvider;
	}
	
	public void updateDayMarkers(List<DayMarker> dayMarkers) {
		ProgressPlaceholder progress = new ProgressPlaceholder();
		UI.getCurrent().addWindow(progress);

		this.data.getDaysList().clear();
		this.data.getDaysList().addAll(dayMarkers);
		this.dayMarkerDataProvider.refreshAll();
		
		progress.close();
	}

	public void updateLengthCombinations(List<LengthCombinationVariant> lengthCombinations) {
		ProgressPlaceholder progress = new ProgressPlaceholder();
		UI.getCurrent().addWindow(progress);

		this.data.getCombinationList().clear();
		this.data.getCombinationList().addAll(lengthCombinations);
		this.combinationDataProvider.refreshAll();
		
		progress.close();
	}
	
	public void updatePeriods(int length, List<Period> periods) {
		ProgressPlaceholder progress = new ProgressPlaceholder();
		UI.getCurrent().addWindow(progress);

		this.data.getPeriods().put(length, periods);
		this.periodDataProvider.refreshAll();
		
		progress.close();
	}

	public void updateVacationRating(Map<Double, List<PeriodCombinationVariant>> vacationRating) {
		ProgressPlaceholder progress = new ProgressPlaceholder();
		UI.getCurrent().addWindow(progress);

		this.data.getVacations().clear();
		this.data.getVacations().putAll(vacationRating);
		this.vacationDataProvider.refreshAll();
		
		progress.close();
	}

}
