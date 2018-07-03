package opt.vacation.services;

import java.util.List;

import opt.vacation.jpa.entities.LengthCombinationVariant;

public interface LengthCombinationService {
	public List<LengthCombinationVariant> getCombinations(int value, int parts);
}
