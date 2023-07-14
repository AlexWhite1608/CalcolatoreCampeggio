package utils;

public enum Stagione {
    AltaStagione(new String[]{"JUNE", "JULY", "AUGUST"}),
    BassaStagione(new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"});

    private final String[] mesi;

    Stagione(String[] mesi) {
        this.mesi = mesi;
    }

    public String[] getMesi() {
        return mesi;
    }
}

