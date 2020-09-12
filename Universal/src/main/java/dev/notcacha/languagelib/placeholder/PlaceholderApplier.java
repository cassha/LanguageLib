package dev.notcacha.languagelib.placeholder;

@FunctionalInterface
public interface PlaceholderApplier {

    /**
     * Set multiple variables in 1 single method to text {@param text}, using {@param holder} to get some value to be set
     */

    String set(Object holder, String text);
}