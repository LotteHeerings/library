package nl.belastingdienst.library.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class DamageController {

    private final DamageService damageService;

    @PostMapping("/damages/{id}")
    public Damage createDamageIncident(@PathVariable(name = "id") Long id, @RequestBody DamageDto damage)
            throws Exception {
        return damageService.createDamageIncident(id, damage);
    }

    @GetMapping("/damages")
    public List<Damage> readAllDamages() {
        return damageService.viewAllDamageIncidents();
    }

    @GetMapping("/unpaidDamages")
    public List<Damage> readUnpaidDamages() {
        return damageService.viewAllUnpaidDamageIncidents();
    }

    @DeleteMapping("/damages/{id}")
    public void deleteDamageIncident(@PathVariable(name = "id") Long id) {
        damageService.deleteDamageIncident(id);
    }

    @PutMapping("/damages/{id}")
    public Damage updateDamageIncident(@PathVariable(name = "id") Long id, @RequestBody DamageDto damageDetails)
            throws Exception {
        return damageService.updateDamageIncident(id, damageDetails);
    }

    @PatchMapping("/damages/{id}")
    public Damage payDamageIncident(@PathVariable(name = "id") Long id) {
        return damageService.updateDamageIncidentPaid(id);
    }
}
