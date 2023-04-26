package nl.belastingdienst.library.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DamageService {

    private final DamageRepository damageRepository;

    public Damage createDamageIncident(Long idBookLendingArchive,DamageDto damageDto){
        var damage = Damage.builder()
                .idBookLendingArchive(idBookLendingArchive)
                .typeOfDamage(damageDto.getTypeOfDamage())
                .costOfDamage(damageDto.getCostOfDamage())
                .paid(damageDto.getPaid())
                .build();

        return damageRepository.save(damage);
    }

    public List<Damage> viewAllDamageIncidents() {
        return damageRepository.findAll();
    }

    public List<Damage> viewAllUnpaidDamageIncidents() {
        return viewAllDamageIncidents().stream()
                .filter(damage -> !damage.getPaid())
                .collect(Collectors.toList());
    }

    public void deleteDamageIncident(Long id) {
        damageRepository.deleteById(id);
    }

    public Damage updateDamageIncident(Long id, DamageDto damageDetails) throws Exception {
        Optional<Damage> damageOptional = damageRepository.findById(id);
        if (damageOptional.isEmpty()){
            throw new Exception("Damage incident doesn't exist");
        }

        Damage damage = damageOptional.get();

        damage.setTypeOfDamage(damageDetails.getTypeOfDamage());
        damage.setCostOfDamage(damageDetails.getCostOfDamage());
        damage.setPaid(damageDetails.getPaid());

        return damageRepository.save(damage);
    }

    public Damage updateDamageIncidentPaid(Long id) {
        Damage damage = damageRepository.findById(id).get();
        damage.setPaid(true);

        return damageRepository.save(damage);
    }

}
