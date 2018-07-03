package opt.vacation.services;

import java.util.List;
import java.util.Map;

import opt.vacation.jpa.entities.PeriodCombinationVariant;

public interface PeriodCombinationService {
	public Map<Double, List<PeriodCombinationVariant>> getPeriodCombinations(int year, int days, int parts);
}
