package ehcruz.s3.model;

public class FileObject {

    private String key;
    private String content;

    public FileObject() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
