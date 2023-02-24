package io.github.WegielWBucie.Notes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("note")
public class NoteConfigurationProperties {
    private Template template;

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleNotes;

        public boolean isAllowMultipleNotes() {
            return this.allowMultipleNotes;
        }
        public void setAllowMultipleNotes(final boolean allowMultipleNotes) {
            this.allowMultipleNotes = allowMultipleNotes;
        }
    }
}
