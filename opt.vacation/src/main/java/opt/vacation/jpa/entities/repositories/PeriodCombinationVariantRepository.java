package opt.vacation.jpa.entities.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.PeriodCombinationVariant;

public interface PeriodCombinationVariantRepository extends JpaRepository<PeriodCombinationVariant, UUID> {
	public List<PeriodCombinationVariant> findAllByYearMarkerAndLengthCombination_Sum(Integer yearMarker, Integer sum);
}
