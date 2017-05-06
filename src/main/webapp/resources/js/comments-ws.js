var stompClient = null;

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        var currentUrl = window.location.pathname;
        stompClient.subscribe('/topic'+ currentUrl, function (commentResponse) {
            var comment = JSON.parse(commentResponse.body);
            showComment(comment);
        });
    });
}

function sendComment() {
    var currentUrl = window.location.pathname;
    stompClient.send("/app"+currentUrl+"/addComment", {}, JSON.stringify({'text': $("#comment-textarea").val()}));
    document.getElementById("comment-textarea").value = "";
}

function showComment(comment) {
    $("div.comments").prepend(
        '<div class="comment-author-time">' +
            '<a href="/users/' + comment.userAuthor.id + '">' +
                '' + comment.userAuthor.firstName +' '+ comment.userAuthor.lastName +
            '</a>'+
            '<div class="time">' +
            "" + comment['commentDate'] +
            '</div>' +
        '</div>' +
        '<div class="comment-text-wrapper">' +
            '<p class="comment-text">'+ comment.text +'</p>' +
        '</div>'
    );
    /*
    $('html,body').animate({
            scrollTop: $("#comment-textarea").offset().top},
        'slow');
        */
}

$(document).ready(function () {
    connect();
    $("#comment-form").on('submit', function (e) {
        e.preventDefault();
        if (document.getElementById("comment-textarea").value != "") {
            sendComment();
        }
    });
});