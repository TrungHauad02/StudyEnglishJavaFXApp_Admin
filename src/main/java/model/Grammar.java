package model;

public class Grammar {
    private String idGrammar;
    private String title;
    private String content;
    private String rule;
    private byte[] image;
    private String example;

    public String getIdGrammar() {
        return idGrammar;
    }

    public void setIdGrammar(String idGrammar) {
        this.idGrammar = idGrammar;
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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
