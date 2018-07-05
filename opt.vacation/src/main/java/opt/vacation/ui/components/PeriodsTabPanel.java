package opt.vacation.ui.components;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.provider.DataChangeEvent;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;

import opt.vacation.beans.DataBinderBean;
import opt.vacation.jpa.entities.Period;
import opt.vacation.services.PeriodService;

@SpringComponent
@UIScope
public class PeriodsTabPanel extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2189566734533986663L;
	public static final String TITLE = "periods";
	public static final String GENERATE_TITLE = "generate periods";
	public static final String LENGTH_TITLE = "length";
	public static final String REAL_LENGTH_TITLE = "real length";
	public static final String FIRST_DAY_TITLE = "first day";
	public static final String FIRST_WEEKDAY_TITLE = "first weekday";
	public static final String LAST_DAY_TITLE = "last day";
	public static final String LAST_WEEKDAY_TITLE = "last weekday";
	public static final String WORK_PRICE_TITLE = "work price";
	public static final String VACATION_PRICE_TITLE = "vacation price";
	public static final String RATING_TITLE = "rating";
	
	private Grid<Entry<Integer, List<Period>>> lengthGrid;
	private Grid<Period> periodGrid;
	private Button generateButton;
	
	@Autowired
	private DataBinderBean dataBinder;
	@Autowired
	private PeriodService periodService;
	
	public PeriodsTabPanel() {
		super();
		this.setCaption(TITLE);
		
		lengthGrid = new Grid<>();
		lengthGrid.setSizeFull();
		lengthGrid.addColumn(Entry<Integer, List<Period>>::getKey, String::valueOf).setCaption(LENGTH_TITLE);
		lengthGrid.setSelectionMode(SelectionMode.SINGLE);
		lengthGrid.addSelectionListener(new LengthSelectionListener());
		
		generateButton = new Button(GENERATE_TITLE, new RefreshClickListener());
		generateButton.setSizeFull();
		
		VerticalLayout ratingLayout = new VerticalLayout();
		ratingLayout.setMargin(false);
		ratingLayout.setSizeFull();
		ratingLayout.addComponents(generateButton, lengthGrid);
		ratingLayout.setExpandRatio(generateButton, 0.05f);
		ratingLayout.setExpandRatio(lengthGrid, 0.95f);
		
		periodGrid = new Grid<>();
		periodGrid.setSizeFull();
		periodGrid.addColumn(Period::getFirstDay, String::valueOf).setCaption(FIRST_DAY_TITLE);
		periodGrid.addColumn((x)->{ return x.getFirstDay().getDayOfWeek(); }, String::valueOf).setCaption(FIRST_WEEKDAY_TITLE);
		periodGrid.addColumn(Period::getLastDay, String::valueOf).setCaption(LAST_DAY_TITLE);
		periodGrid.addColumn((x)->{ return x.getLastDay().getDayOfWeek(); }, String::valueOf).setCaption(LAST_WEEKDAY_TITLE);
		periodGrid.addColumn(Period::getOfficialLength, String::valueOf).setCaption(LENGTH_TITLE);
		periodGrid.addColumn(Period::getActualLength, String::valueOf).setCaption(REAL_LENGTH_TITLE);
		periodGrid.addColumn(Period::getWorkPrice, String::valueOf).setCaption(WORK_PRICE_TITLE);
		periodGrid.addColumn(Period::getVacationPrice, String::valueOf).setCaption(VACATION_PRICE_TITLE);
		periodGrid.addColumn((x)->{ return x.getVacationPrice() - x.getWorkPrice(); }, String::valueOf).setCaption(RATING_TITLE);
		periodGrid.setSelectionMode(SelectionMode.SINGLE); 
		
		this.addComponents(ratingLayout, periodGrid);
		this.setExpandRatio(ratingLayout, 0.2f);
		this.setExpandRatio(periodGrid, 0.8f);
		
	}
	
	@PostConstruct
	protected void init() {
		lengthGrid.setDataProvider(dataBinder.getPeriodDataProvider());
		dataBinder.getPeriodDataProvider().addDataProviderListener(new EntryDataProviderListener());
		periodGrid.setItems(Collections.emptyList());
	}
	
	private class LengthSelectionListener implements SelectionListener<Entry<Integer, List<Period>>> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2039192958737460766L;

		@Override
		public void selectionChange(SelectionEvent<Entry<Integer, List<Period>>> event) {
			if (event.getFirstSelectedItem().isPresent()) {
				List<Period> list = event.getFirstSelectedItem().get().getValue();
				periodGrid.setItems(list != null ? list : Collections.emptyList());
			} else {
				periodGrid.setItems(Collections.emptyList());
			}
		}
	}
	
	private class EntryDataProviderListener implements DataProviderListener<Entry<Integer, List<Period>>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2039216820442398163L;

		@Override
		public void onDataChange(DataChangeEvent<Entry<Integer, List<Period>>> event) {
			periodGrid.setItems(Collections.emptyList());
		}
		
	}
	
	private class RefreshClickListener implements ClickListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5479642137870288585L;

		@Override
		public void buttonClick(ClickEvent event) {
			dataBinder.getData().getPeriods().clear();
			for (int i = 1; i <= dataBinder.getData().getDays(); i++) {
				dataBinder.updatePeriods(i, periodService.getPeriods(dataBinder.getData().getYear(), i));
			}
		}

	}
}

