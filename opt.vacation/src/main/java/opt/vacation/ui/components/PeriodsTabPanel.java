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
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.ItemClickListener;

import opt.vacation.beans.DataBinderBean;
import opt.vacation.jpa.entities.Period;

@SpringComponent
@UIScope
public class PeriodsTabPanel extends HorizontalLayout {

	public static final String TITLE = "periods";
	public static final String LENGTH_TITLE = "length";
	public static final String REAL_LENGTH_TITLE = "real length";
	public static final String FIRST_DAY_TITLE = "first day";
	public static final String LAST_DAY_TITLE = "last day";
	public static final String WORK_PRICE_TITLE = "work price";
	public static final String VACATION_PRICE_TITLE = "vacation price";
	
	private Grid<Entry<Integer, List<Period>>> lengthGrid;
	private Grid<Period> periodGrid;
	
	@Autowired
	private DataBinderBean dataBinder;
	
	public PeriodsTabPanel() {
		super();
		this.setCaption(TITLE);
		
		lengthGrid = new Grid<>();
		lengthGrid.setSizeFull();
		lengthGrid.addColumn(Entry<Integer, List<Period>>::getKey, String::valueOf).setCaption(LENGTH_TITLE);
		lengthGrid.setSelectionMode(SelectionMode.SINGLE);
		lengthGrid.addSelectionListener(new LengthSelectionListener());
		
		periodGrid = new Grid<>();
		periodGrid.setSizeFull();
		periodGrid.addColumn(Period::getFirstDay, String::valueOf).setCaption(FIRST_DAY_TITLE);
		periodGrid.addColumn(Period::getLastDay, String::valueOf).setCaption(LAST_DAY_TITLE);
		periodGrid.addColumn(Period::getOfficialLength, String::valueOf).setCaption(LENGTH_TITLE);
		periodGrid.addColumn(Period::getActualLength, String::valueOf).setCaption(REAL_LENGTH_TITLE);
		periodGrid.addColumn(Period::getWorkPrice, String::valueOf).setCaption(WORK_PRICE_TITLE);
		periodGrid.addColumn(Period::getVacationPrice, String::valueOf).setCaption(VACATION_PRICE_TITLE);
		periodGrid.setSelectionMode(SelectionMode.SINGLE); 
		
		this.addComponents(lengthGrid, periodGrid);
		this.setExpandRatio(lengthGrid, 0.2f);
		this.setExpandRatio(periodGrid, 0.8f);
		
	}
	
	@PostConstruct
	protected void init() {
		lengthGrid.setDataProvider(dataBinder.getPeriodDataProvider());
		dataBinder.getPeriodDataProvider().addDataProviderListener(new EntryDataProviderListener());
		periodGrid.setItems(Collections.emptyList());
	}
	
	private class LengthSelectionListener implements SelectionListener<Entry<Integer, List<Period>>> {
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

		@Override
		public void onDataChange(DataChangeEvent<Entry<Integer, List<Period>>> event) {
			periodGrid.setItems(Collections.emptyList());
		}
		
	}
}

