package ir.awlrhm.modules.view.datePicker;

import ir.awlrhm.modules.utils.calendar.PersianCalendar;

public interface CalendarActionListener {
    void onDateSelected(PersianCalendar persianCalendar);
    void onDismissed();
}
