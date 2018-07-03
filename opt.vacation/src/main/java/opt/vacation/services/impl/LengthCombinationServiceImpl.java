package opt.vacation.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.spring.annotation.SpringComponent;

import opt.vacation.jpa.entities.LengthCombinationVariant;
import opt.vacation.jpa.entities.repositories.LengthCombinationVariantRepository;
import opt.vacation.services.LengthCombinationService;

@SpringComponent
public class LengthCombinationServiceImpl implements LengthCombinationService {

	@Autowired
	private LengthCombinationVariantRepository lengthCombiRepo;
	
	@Override
	@Transactional
	public List<LengthCombinationVariant> getCombinations(int value, int parts) {
		List<LengthCombinationVariant> result = lengthCombiRepo.findAllBySumAndParts(value, parts);
		if (result.isEmpty()) {
			result = generateCombinations(value, parts);
			result = lengthCombiRepo.save(result);
		}
		return result;
	}

	private List<LengthCombinationVariant> generateCombinations(int value, int parts) {
		int length = 0;
		int min = 0;
		int max = 0;
		
		List<LengthCombinationVariant> state = null;
		List<LengthCombinationVariant> prevState = null;
		
		while (length < parts) {
			length++;
			
			if (state != null) {
				prevState = state;
				state = new LinkedList<LengthCombinationVariant>();
				
				for (LengthCombinationVariant prototype : prevState) {
					max = (value - prototype.getSum()) / (parts - length + 1);
					min = length < parts ? prototype.getMax() : max;
					for (int i = min; i <= max; i++) {
						state.add(new LengthCombinationVariant(i, prototype));
					}
				}
				
			} else {
				state = new LinkedList<LengthCombinationVariant>();
				
				min = 1;
				max = value / parts;
				for (int i = min; i <= max; i++) {
					state.add(new LengthCombinationVariant(i));
				}
			}
			
		}
		
		int i=1;
		for (LengthCombinationVariant variant : state) {
			variant.setVariantId(i);
			i++;
		}
		
		return state;
	}	
}
