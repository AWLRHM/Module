package ir.awlrhm.modules.view;

import ir.awlrhm.modules.utils.calendar.PersianCalendar;

public interface Listener {
    void onDateSelected(PersianCalendar persianCalendar);

    void onDismissed();
}
