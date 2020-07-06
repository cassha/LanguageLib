package dev.notcacha.languagelib.bukkit.message;

import dev.notcacha.languagelib.bukkit.BukkitLanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LanguageTranslatableMessage implements TranslatableMessage {

    private final String path;
    private final BukkitLanguageLib<YamlConfiguration> bukkitLanguageLib;
    private final Map<String, String> variables;

    public LanguageTranslatableMessage(@NotNull String path, @NotNull BukkitLanguageLib<YamlConfiguration> bukkitLanguageLib) {
        this.path = path;
        this.bukkitLanguageLib = bukkitLanguageLib;
        this.variables = new ConcurrentHashMap<>();
    }

    @Override
    public @NotNull String getPath() {
        return this.path;
    }

    @Override
    public @NotNull String getMessage(@NotNull String language) {
        String messageTranslate;

        if (this.bukkitLanguageLib.getTranslateManager().containsFile(language)) {
            messageTranslate = this.bukkitLanguageLib.getFile(language).getString(getPath());
        } else {
            messageTranslate = this.bukkitLanguageLib.getDefaultFile().getString(getPath());
        }
        for (String key : variables.keySet()) {
            String value = variables.get(key);
            messageTranslate = messageTranslate.replace(key, value);
        }

        return messageTranslate;
    }

    @Override
    public @NotNull List<String> getMessages(@NotNull String language) {
        List<String> messageTranslate;

        if (this.bukkitLanguageLib.getTranslateManager().containsFile(language)) {
            messageTranslate = this.bukkitLanguageLib.getFile(language).getStringList(getPath());
        } else {
            messageTranslate = this.bukkitLanguageLib.getDefaultFile().getStringList(getPath());
        }

        for (String key : variables.keySet()) {
            String value = variables.get(key);
            messageTranslate.replaceAll(message -> message.replace(key, value));
        }

        return messageTranslate;
    }

    @Override
    public @NotNull TranslatableMessage setVariable(@NotNull String key, @NotNull String value) {
        this.variables.put(key, value);
        return this;
    }
}
