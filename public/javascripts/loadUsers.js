$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
    loadUsers();
    $('#btnUpdateName').on('click', function(){
        var newUsername = $('#inputName').val();
        $.ajax({
            url: "/board/updateUsername",
            data: {newUsername: newUsername},
            method: 'post',
            context: document.body
        }).done(function (users) {

        });

        $('#userModal').modal('hide');
    });


    $('.card').on('click', function(){
        $('.card').each(function(){
            $(this).attr('class', 'btn btn-info card');
            $(this).attr('disabled',"");
        });

        $(this).attr('class', 'btn btn-danger card');
        console.log(this.innerHTML);
        $.ajax({
            url: "/board/" + window.location.href.split("/")[4] + "/poker/create",
            data: {cardIndex: this.innerHTML},
            method: 'post',
            context: document.body
        }).done(function (result) {
        });
    });


});

function showPopup(){
    console.log("tu");

}



function loadUsers() {
    $.ajax({
        url: "/board/" + window.location.href.split("/")[4] + "/users",
        context: document.body
    }).done(function (users) {
        var listUsers = $('#listUsers');
        listUsers.html('');
        var array = JSON.parse(users);
        var html;
        for (var i = 0; i < array.length; i++) {
            html = '<li class=""><a href="index.html"><span class="menu-text .spanUsername">' + array[i].name + '</span></a><b class="arrow"></b></li>';
            listUsers.append(html);
        }

    });
}


var wsUri = "ws://localhost:9000/sock";
var output;

function init()
{
    output = document.getElementById("output");
    testWebSocket();
}
function testWebSocket()
{
    websocket = new WebSocket(wsUri);
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.send("1" + window.location.href.split("/")[4]);
}


function onMessage(evt){
    console.log('jozo');
    console.log(evt);

}


window.addEventListener("load", init, false);
