package models;

/**
 * Created by Rex on 21.4.2015.
 */
public class Item {
    private Long id;
    private RetrospectiveType tag;
    private String text;
    private boolean ready;
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RetrospectiveType getTag() {
        return tag;
    }

    public void setTag(RetrospectiveType tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
