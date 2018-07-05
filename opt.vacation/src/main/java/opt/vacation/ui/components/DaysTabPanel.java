package opt.vacation.ui.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;

import opt.vacation.beans.DataBinderBean;
import opt.vacation.jpa.entities.DayMarker;

@SpringComponent
@UIScope
public class DaysTabPanel extends Grid<DayMarker> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4121538092112039673L;
	private static final String CAPTION = "days";
	private static final String DATE_COLUMN_CAPTION = "Date";
	private static final String DAY_OF_WEEK_COLUMN_CAPTION = "Day of week";
	private static final String IS_HOLIDAY_COLUMN_CAPTION = "Is holiday";
	private static final String WORK_PRICE_COLUMN_CAPTION = "Work price";
	private static final String VACATION_PRICE_COLUMN_CAPTION = "Vacation price";
	
	@Autowired
	private DataBinderBean dataBinder;
	
	public DaysTabPanel() {
		super();
		
		this.setCaption(CAPTION);
		
		this.setSelectionMode(SelectionMode.NONE);
		
		this.addColumn(DayMarker::getDateMarker, String::valueOf).setCaption(DATE_COLUMN_CAPTION);
		this.addColumn(DayMarker::getDayOfWeek, String::valueOf).setCaption(DAY_OF_WEEK_COLUMN_CAPTION);
		this.addColumn(DayMarker::getIsHoliday, String::valueOf).setCaption(IS_HOLIDAY_COLUMN_CAPTION);
		this.addColumn(DayMarker::getWorkPrice, String::valueOf).setCaption(WORK_PRICE_COLUMN_CAPTION);
		this.addColumn(DayMarker::getVacationPrice, String::valueOf).setCaption(VACATION_PRICE_COLUMN_CAPTION);
	}
	
	@PostConstruct
	protected void init() {
		this.setDataProvider(this.dataBinder.getDayMarkerDataProvider());
	}
	
}
