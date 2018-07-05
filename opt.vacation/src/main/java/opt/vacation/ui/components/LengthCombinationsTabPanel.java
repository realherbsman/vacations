package opt.vacation.ui.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;

import opt.vacation.beans.DataBinderBean;
import opt.vacation.jpa.entities.LengthCombinationVariant;

@SpringComponent
@UIScope
public class LengthCombinationsTabPanel extends Grid<LengthCombinationVariant> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5115177938664420541L;

	private static final String CAPTION = "length combo";
	
	private static final String SUM_CAPTION = "Sum";
	private static final String PARTS_CAPTION = "Parts";
	private static final String COMBINATION_CAPTION = "Combination";
	
	@Autowired
	private DataBinderBean dataBinder;
	
	public LengthCombinationsTabPanel() {
		super();
		
		this.setCaption(CAPTION);
		
		this.setSelectionMode(SelectionMode.SINGLE);
		
		this.addColumn(LengthCombinationVariant::getSum, String::valueOf).setCaption(SUM_CAPTION);
		this.addColumn(LengthCombinationVariant::getParts, String::valueOf).setCaption(PARTS_CAPTION);
		this.addColumn(LengthCombinationVariant::getElements, String::valueOf).setCaption(COMBINATION_CAPTION);
	}
	
	@PostConstruct
	protected void init() {
		this.setDataProvider(this.dataBinder.getLengthCombinationDataProvider());
	}
	
}
