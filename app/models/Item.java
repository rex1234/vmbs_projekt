package models;

/**
 * Created by Rex on 21.4.2015.
 */
public class Item {
    private Long id;
    private Long boardId;
    private Long ownerId;

    //poker
    private String cardIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(String cardIndex) {
        this.cardIndex = cardIndex;
    }
}
