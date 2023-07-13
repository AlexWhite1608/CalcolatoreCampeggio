package utils;

import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class SampleHighlightPolicy implements DateHighlightPolicy {

    @Override
    public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
        LocalDate today = LocalDate.now();

        // Confronta la data fornita con la data odierna
        if (date.equals(today)) {
            return new HighlightInformation(Color.RED, null);
        }

        // Altrimenti, non viene effettuato alcun highlight
        return null;
    }
}
