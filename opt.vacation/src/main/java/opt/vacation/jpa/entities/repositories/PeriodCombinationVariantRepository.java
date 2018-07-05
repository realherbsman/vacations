package opt.vacation.jpa.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.PeriodCombinationVariant;
import opt.vacation.jpa.entities.ids.PeriodCombinationVariantId;

public interface PeriodCombinationVariantRepository extends JpaRepository<PeriodCombinationVariant, PeriodCombinationVariantId> {
	public List<PeriodCombinationVariant> findAllByYearMarkerAndLcSum(Integer yearMarker, Integer lcSum);
}
