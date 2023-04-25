package nl.belastingdienst.library;

import lombok.RequiredArgsConstructor;
import nl.belastingdienst.library.bookLendingArchive.BookLendingArchiveService;
import nl.belastingdienst.library.damage.DamageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MiscController {

    private final BookLendingArchiveService bookLendingArchiveService;

    private final DamageService damageService;

}
