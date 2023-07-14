package utils;

public enum Stagione {
    AltaStagione(new String[]{"Giugno", "Luglio", "Agosto", "Settembre"}),
    BassaStagione(new String[]{"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Ottobre", "Novembre", "Dicembre"});

    private final String[] mesi;

    Stagione(String[] mesi) {
        this.mesi = mesi;
    }

    public String[] getMesi() {
        return mesi;
    }
}

