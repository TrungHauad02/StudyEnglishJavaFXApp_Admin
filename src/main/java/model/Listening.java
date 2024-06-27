package model;

import java.util.Date;

public class Listening {
    private String idListening;
    private String title;
    private String description;
    private byte[] video;
    private String script;

    public String getIdListening() {
        return idListening;
    }

    public void setIdListening(String idListening) {
        this.idListening = idListening;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}

