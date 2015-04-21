package services;

import models.Board;
import models.Item;
import models.Member;
import models.RestId;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Rex on 21.4.2015.
 */
public interface RetrofitInterface {

    //boards

    @GET("/boards/{id}")
    Board getBoard(@Path("id") Long id);

    @GET("/boards/")
    List<Board> getBoards();

    @POST("/boards/")
    RestId createBoard(@Body Board board);

    @PUT("/boards/{id}")
    Board updateBoard(@Path("id") Long id, @Body Board board);

    @DELETE("/boards/{id}")
    String deleteBoard(@Path("id") Long id);

    //items

    @GET("/boards/{boardid}/items/{itemid}")
    Item getItemForBoard(@Path("boardid") Long boardId, @Path("itemid") Long itemId);

    @GET("/boards/{boardid}/items")
    List<Item> getItemsForBoard(@Path("boardid") Long boardId);

    @POST("/boards/{boardid}/items")
    RestId createItem(@Path("boardid") Long boardID, @Body Item item);

    @PUT("/boards/{boardid}/items/{itemid}")
    Item updateItem(@Path("boardid") Long boardID, @Path("itemid") Long itemId, @Body Item item);

    @DELETE("/boards/{boardid}/items/{itemid}")
    String deleteItem(@Path("boardid") Long boardID, @Path("itemid") Long itemId);

    //members

    @GET("/boards/{boardid}/members/{memberid}")
    Member getMemberForBoard(@Path("boardid") Long boardId, @Path("memberid") Long memberId);

    @GET("/boards/{boardid}/members")
    List<Member> getMembersForBoard(@Path("boardid") Long boardId);

    @POST("/boards/{boardid}/members")
    RestId createMember(@Path("boardid") Long boardID, @Body Member member);

    @PUT("/boards/{boardid}/members/{memberid}")
    Member updateMember(@Path("boardid") Long boardID, @Path("memberid") Long memberId, @Body Member member);

    @DELETE("/boards/{boardid}/members/{memberid}")
    String deleteMember(@Path("boardid") Long boardID, @Path("memberid") Long memberId);
}
