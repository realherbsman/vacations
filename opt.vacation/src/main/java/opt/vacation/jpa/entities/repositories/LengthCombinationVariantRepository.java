package opt.vacation.jpa.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.LengthCombinationVariant;
import opt.vacation.jpa.entities.ids.LengthCombinationVariantId;

public interface LengthCombinationVariantRepository extends JpaRepository<LengthCombinationVariant, LengthCombinationVariantId> {
	public List<LengthCombinationVariant> findAllBySumAndParts(Integer sum, Integer parts);
}
