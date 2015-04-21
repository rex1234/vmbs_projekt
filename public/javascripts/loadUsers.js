$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
    loadUsers();
    $('#btnUpdateName').on('click', function(){
        var newUsername = $('#inputName').val();
        $.ajax({
            url: "/board/updateUsername",
            data: newUsername,
            method: 'post',
            context: document.body
        }).done(function (users) {

        });

        $('#userModal').modal('hide');
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
