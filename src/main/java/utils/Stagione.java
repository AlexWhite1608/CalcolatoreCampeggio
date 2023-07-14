package utils;

public enum Stagione {
    AltaStagione(new String[]{"JUNE", "JULY", "AUGUST", "SEPTEMBER"}),
    BassaStagione(new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "OCTOBER", "NOVEMBER", "DECEMBER"});

    private final String[] mesi;

    Stagione(String[] mesi) {
        this.mesi = mesi;
    }

    public String[] getMesi() {
        return mesi;
    }
}

