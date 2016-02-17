package Calendar.CalendarRules;


public enum DayType {

    Falta, Baixa, Ferias, Folga;

    static {
        Falta.isTypeParametrized = false;
        Baixa.isTypeParametrized = false;
        Ferias.isTypeParametrized = true;
        Folga.isTypeParametrized = true;
    }
    private boolean isTypeParametrized;

    public boolean hasParameters() {
        return isTypeParametrized;
    }
}
