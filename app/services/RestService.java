package services;

import models.Board;
import models.Item;
import models.Member;
import models.RestId;
import retrofit.RestAdapter;

import java.util.List;

/**
 * Created by Rex on 21.4.2015.
 */
public class RestService {

    private static final String REST_ENDPOINT = "http://goagile.cngroup.dk/vmbs";
    private static RestService instance;

    private RetrofitInterface api;

    private RestService() {
        api = new RestAdapter.Builder()
                .setEndpoint(REST_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String s) {
                        System.out.println(s);
                    }
                })
                .build()
                .create(RetrofitInterface.class);
    }

    public static RestService getInstance() {
        if(instance == null) {
            instance = new RestService();
        }
        return instance;
    }

    public Board getBoard(Long id) {
        return api.getBoard(id);
    }

    public List<Board> getBoards() {
        return api.getBoards();
    }

    public void createBoard(Board board) {
        RestId resp = api.createBoard(board);
        board.setId(resp.getId());
    }

    public void updateBoard(Board board) {
        api.updateBoard(board.getId(), board);
    }

    public void deleteBoard(Long id) {
        api.deleteBoard(id);
    }

    public Item getItemForBoard(Long boardId, Long itemId) {
        return api.getItemForBoard(boardId, itemId);
    }

    public List<Item> getItemsForBoard(Long boardId) {
        return api.getItemsForBoard(boardId);
    }

    public void createItem(Long boardID, Item item) {
        RestId resp = api.createItem(boardID, item);
        item.setId(resp.getId());
    }

    public void updateItem(Long boardID, Item item) {
        api.updateItem(boardID, item.getId(), item);
    }

    public void deleteItem(Long boardID, Long itemId) {
        api.deleteItem(boardID, itemId);
    }

    public Member getMemberForBoard(Long boardId, Long memberId) {
        return api.getMemberForBoard(boardId, memberId);
    }

    public List<Member> getMembersForBoard() {
        return api.getMembersForBoard();
    }

    public void createMember(Long boardID, Member member) {
        RestId resp = api.createMember(boardID, member);
        member.setId(resp.getId());
    }

    public void updateMember(Long boardID, Member member) {
        api.updateMember(boardID, member.getId(), member);
    }

    public void deleteMember(Long boardID, Long memberId) {
        api.deleteMember(boardID, memberId);
    }

}
