package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCsv(File csvFile);
}
