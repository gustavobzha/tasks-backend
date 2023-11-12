import br.ce.wcaquino.taskbackend.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateUtilsTest {

    @Test
    public void deveRetornarTrueParaDatasFuturas() {
        LocalDate date = LocalDate.of(2050, 1, 1);
        Assertions.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarFalseParaDatasPassadas() {
        LocalDate date = LocalDate.of(2010, 1, 1);
        Assertions.assertFalse(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarTrueParaDataAtual() {
        LocalDate date = LocalDate.now();
        Assertions.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }
}
