package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/info")
class InfoController {

    private final DataSourceProperties dataSource;

    private final NoteConfigurationProperties myProp;

    InfoController(final DataSourceProperties dataSource, final NoteConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/url")
    String url() {
        return this.dataSource.getUrl();
    }

    @GetMapping("/prop")
    boolean myProp() {
        return this.myProp.getTemplate().isAllowMultipleNotes();
    }
}
