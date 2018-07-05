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
import opt.vacation.jpa.entities.PeriodCombinationVariant;
import opt.vacation.services.PeriodCombinationService;

@SpringComponent
@UIScope
public class VacationRatingTabPanel extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2467763892559550677L;
	public static final String TITLE = "vacation rating";
	public static final String GENERATE_TITLE = "generate rating";
	public static final String VACATION_RATING_TITLE = "rating";
	public static final String VACATION_UUID_TITLE = "UUID";
	public static final String VACATION_LENGTH_TITLE = "DURATION";
	public static final String PERIOD_LENGTH_TITLE = "length";
	public static final String PERIOD_REAL_LENGTH_TITLE = "real length";
	public static final String PERIOD_FIRST_DAY_TITLE = "first day";
	public static final String PERIOD_LAST_DAY_TITLE = "last day";
	public static final String PERIOD_WORK_PRICE_TITLE = "work price";
	public static final String PERIOD_VACATION_PRICE_TITLE = "vacation price";
	
	private Grid<Entry<Double, List<PeriodCombinationVariant>>> ratingGrid;
	private Grid<PeriodCombinationVariant> vacationGrid;
	private Grid<Period> periodGrid;
	private Button generateButton;
	
	@Autowired
	private DataBinderBean dataBinder;
	@Autowired
	private PeriodCombinationService periodCombinationService;
	
	public VacationRatingTabPanel() {
		super();
		this.setCaption(TITLE);
		
		ratingGrid = new Grid<>();
		ratingGrid.setSizeFull();
		ratingGrid.addColumn(Entry<Double, List<PeriodCombinationVariant>>::getKey, String::valueOf).setCaption(VACATION_RATING_TITLE);
		ratingGrid.setSelectionMode(SelectionMode.SINGLE);
		ratingGrid.addSelectionListener(new RatingSelectionListener());
		
		generateButton = new Button(GENERATE_TITLE, new RefreshClickListener());
		generateButton.setSizeFull();
		
		VerticalLayout ratingLayout = new VerticalLayout();
		ratingLayout.setSizeFull();
		ratingLayout.addComponents(generateButton, ratingGrid);
		ratingLayout.setExpandRatio(generateButton, 0.05f);
		ratingLayout.setExpandRatio(ratingGrid, 0.95f);
		
		vacationGrid = new Grid<>();
		vacationGrid.setSizeFull();
		vacationGrid.addColumn(PeriodCombinationVariant::getVariantId, String::valueOf).setCaption(VACATION_UUID_TITLE);
		vacationGrid.addColumn(PeriodCombinationVariant::getRatingDelta, String::valueOf).setCaption(VACATION_RATING_TITLE);
		vacationGrid.addColumn(PeriodCombinationVariant::getLengthCombination, (x) -> { return String.format("%s:%s", x.getSum(), x.getElements()); }).setCaption(VACATION_LENGTH_TITLE);
		vacationGrid.setSelectionMode(SelectionMode.SINGLE);
		vacationGrid.addSelectionListener(new VacationSelectionListener());
		
		periodGrid = new Grid<>();
		periodGrid.setSizeFull();
		periodGrid.addColumn(Period::getFirstDay, String::valueOf).setCaption(PERIOD_FIRST_DAY_TITLE);
		periodGrid.addColumn(Period::getLastDay, String::valueOf).setCaption(PERIOD_LAST_DAY_TITLE);
		periodGrid.addColumn(Period::getOfficialLength, String::valueOf).setCaption(PERIOD_LENGTH_TITLE);
		periodGrid.addColumn(Period::getActualLength, String::valueOf).setCaption(PERIOD_REAL_LENGTH_TITLE);
		periodGrid.addColumn(Period::getWorkPrice, String::valueOf).setCaption(PERIOD_WORK_PRICE_TITLE);
		periodGrid.addColumn(Period::getVacationPrice, String::valueOf).setCaption(PERIOD_VACATION_PRICE_TITLE);
		periodGrid.setSelectionMode(SelectionMode.SINGLE);
		
		VerticalLayout vacationLayout = new VerticalLayout();
		vacationLayout.setSizeFull();
		vacationLayout.addComponents(vacationGrid, periodGrid);
		vacationLayout.setExpandRatio(vacationGrid, 0.7f);
		vacationLayout.setExpandRatio(periodGrid, 0.3f);
		
		this.addComponents(ratingLayout, vacationLayout);
		this.setExpandRatio(ratingLayout, 0.2f);
		this.setExpandRatio(vacationLayout, 0.8f);
	}
	
	@PostConstruct
	protected void init() {
		ratingGrid.setDataProvider(dataBinder.getVacationDataProvider());
		
		dataBinder.getPeriodDataProvider().addDataProviderListener(new EntryDataProviderListener());
		vacationGrid.setItems(Collections.emptyList());
		periodGrid.setItems(Collections.emptyList());
	}
	
	private class RatingSelectionListener implements SelectionListener<Entry<Double, List<PeriodCombinationVariant>>> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5908368234616151364L;

		@Override
		public void selectionChange(SelectionEvent<Entry<Double, List<PeriodCombinationVariant>>> event) {
			if (event.getFirstSelectedItem().isPresent()) {
				List<PeriodCombinationVariant> list = event.getFirstSelectedItem().get().getValue();
				vacationGrid.setItems(list != null ? list : Collections.emptyList());
			} else {
				vacationGrid.setItems(Collections.emptyList());
			}
		}
	}
	
	private class VacationSelectionListener implements SelectionListener<PeriodCombinationVariant> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5448151833946744418L;

		@Override
		public void selectionChange(SelectionEvent<PeriodCombinationVariant> event) {
			if (event.getFirstSelectedItem().isPresent()) {
				List<Period> list = event.getFirstSelectedItem().get().getPeriods();
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
		private static final long serialVersionUID = 5686611370678299250L;

		@Override
		public void onDataChange(DataChangeEvent<Entry<Integer, List<Period>>> event) {
			vacationGrid.setItems(Collections.emptyList());
			periodGrid.setItems(Collections.emptyList());
		}
		
	}
	
	private class RefreshClickListener implements ClickListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1387495053494510640L;

		@Override
		public void buttonClick(ClickEvent event) {
			dataBinder.updateVacationRating(
					periodCombinationService.getPeriodCombinations(
							dataBinder.getData().getYear(), 
							dataBinder.getData().getDays(), 
							dataBinder.getData().getParts()));
		}

	}
}
