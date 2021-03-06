package opt.vacation.services.impl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.spring.annotation.SpringComponent;

import opt.vacation.jpa.entities.LengthCombinationVariant;
import opt.vacation.jpa.entities.Period;
import opt.vacation.jpa.entities.PeriodCombinationVariant;
import opt.vacation.jpa.entities.repositories.PeriodCombinationVariantRepository;
import opt.vacation.services.LengthCombinationService;
import opt.vacation.services.PeriodCombinationService;
import opt.vacation.services.PeriodService;

@SpringComponent
public class PeriodCombinationServiceImpl implements PeriodCombinationService {

	private Logger logger = LoggerFactory.getLogger(PeriodCombinationServiceImpl.class);
	
	@Autowired
	private PeriodService periodService;
	@Autowired
	private LengthCombinationService lengthCombinationService;
	@Autowired
	private PeriodCombinationVariantRepository periodCombiRepo;

	@Override
	@Transactional
	public Map<Double, List<PeriodCombinationVariant>> getPeriodCombinations(int year, int days, int parts) {
		Map<Double, List<PeriodCombinationVariant>> result = new LinkedHashMap<>();
		List<PeriodCombinationVariant> plainList = periodCombiRepo
				.findAllByYearMarkerAndLcSum(year, days);
		
		if (plainList.isEmpty()) {
			plainList = this.generatePeriodCombinations(year, days, parts);
			//plainList = periodCombiRepo.save(plainList);
			if (logger.isInfoEnabled()) { logger.info("saved"); }
		}
		if (logger.isInfoEnabled()) { logger.info("mapping"); }
		for (PeriodCombinationVariant variant : plainList) {
			List<PeriodCombinationVariant> ratingGroup = result.get(variant.getRatingDelta());
			if (ratingGroup == null) {
				ratingGroup = new LinkedList<>();
				result.put(variant.getRatingDelta(), ratingGroup);
			}
			ratingGroup.add(variant);
		}
		if (logger.isInfoEnabled()) { logger.info("mapped"); }
		return result;
	}

	private List<PeriodCombinationVariant> generatePeriodCombinations(int year, int days, int parts) {
		if (logger.isInfoEnabled()) { logger.info(String.format("generate %s %s %s", year, days, parts)); }
		Map<Integer, List<Period>> periodMap = this.getPeriodMap(year, days);
		if (logger.isInfoEnabled()) { logger.info(String.format("periodMap %s %s", year, days)); }
		List<LengthCombinationVariant> lengthCombinationList = this.getLengthCombinations(days, parts);
		if (logger.isInfoEnabled()) { logger.info(String.format("lengthCombinationList %s %s", days, parts)); }
		
		List<PeriodCombinationVariant> result = new LinkedList<>();
		for (LengthCombinationVariant template : lengthCombinationList) {
			result.addAll(
					periodCombiRepo.save(
							this.getPeriodCombinations(year, template, periodMap)));
		}

		return result;
	}

	private Map<Integer, List<Period>> getPeriodMap(int year, int days) {
		Map<Integer, List<Period>> result = new LinkedHashMap<>();
		for (int i = 1; i <= days; i++) {
			result.put(i, periodService.getPeriods(year, i));
		}
		return result;
	}

	private List<LengthCombinationVariant> getLengthCombinations(int days, int parts) {
		return lengthCombinationService.getCombinations(days, parts);
	}

	private Set<PeriodCombinationVariant> getPeriodCombinations(int year, LengthCombinationVariant template,
			Map<Integer, List<Period>> templateVariants) {

		Set<PeriodCombinationVariant> state = null;
		Set<PeriodCombinationVariant> prevState = null;
		for (int length : template.getElements()) {
			prevState = state;
			state = new LinkedHashSet<>();

			List<Period> periods = templateVariants.get(length);
			for (Period period : periods) {
				if (prevState == null) {
					PeriodCombinationVariant variant = prepareVariant(year, template, period, null);
					if (variant != null) {
						state.add(variant);
					}
				} else {
					for (PeriodCombinationVariant prototype : prevState) {
						PeriodCombinationVariant variant = prepareVariant(year, template, period, prototype);
						if (variant != null) {
							state.add(variant);
						}
					}
				}
			}
		}

		int i = 1;
		for (PeriodCombinationVariant variant : state) {
			variant.setVariantId(i++);
		}
		
		return state;
	}

	private PeriodCombinationVariant prepareVariant(int year, LengthCombinationVariant template, Period period, PeriodCombinationVariant prototype) {
		PeriodCombinationVariant result = null;
		if (isAcceptable(prototype, period)) {
			if (prototype != null) {
				result = new PeriodCombinationVariant(period, prototype);
			} else {
				result = new PeriodCombinationVariant(period, year, template);
				
			}
		}
		return result;
	}

	private boolean isAcceptable(PeriodCombinationVariant prototype, Period candidate) {
		if (prototype != null) {
			for (Period period : prototype.getPeriods()) {
				if (candidate.getLastDay().isBefore(period.getFirstDay()) || candidate.getFirstDay().isAfter(period.getLastDay())) {
					continue;
				}
				return false;
			}
		}
		return true;
	}

}
