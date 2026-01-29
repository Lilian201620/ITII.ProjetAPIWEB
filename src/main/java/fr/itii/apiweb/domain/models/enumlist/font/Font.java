package fr.itii.apiweb.domain.models.enumlist.font;

public enum Font {
    RESET( "\u001B[0m"),
    BOLD("\u001B[1m"),
    ITALIC("\u001B[3m"),
    CYAN("\u001B[36m"),
    GREEN( "\u001B[32m"),
    YELLOW( "\u001B[33m"),
    RED("\u001B[31m"),
    GREY("\u001B[90m");

    private final String font;

    Font(String font) {
        this.font = font;
    }

    public String getFont() {
        return font;
    }

}
