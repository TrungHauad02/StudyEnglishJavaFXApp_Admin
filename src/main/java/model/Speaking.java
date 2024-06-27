package model;

public class Speaking {
    private String idSpeaking;
    private String title;
    private String content;
    private byte[] example;

    public String getIdSpeaking() {
        return idSpeaking;
    }

    public void setIdSpeaking(String idSpeaking) {
        this.idSpeaking = idSpeaking;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getExample() {
        return example;
    }

    public void setExample(byte[] example) {
        this.example = example;
    }

}
