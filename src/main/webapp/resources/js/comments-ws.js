var stompClient = null;
console.log("CONTEXT PATH IS: "+contextPath);
function connect() {
    var currentUrl = window.location.pathname;
    var wsEndpointURL;
    if(currentUrl.indexOf(contextPath + "/")===0 && contextPath !== "/"){
        wsEndpointURL = contextPath + "/stomp-endpoint";
        currentUrl = currentUrl.replace(contextPath, "");
    } else {
        wsEndpointURL = "/stomp-endpoint";
    }
    var socket = new SockJS(wsEndpointURL);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic' + currentUrl, function (commentResponse) {
            var comment = JSON.parse(commentResponse.body);
            showComment(comment);
        });
    });
}

function sendComment() {
    var currentUrl1 = window.location.pathname;
    if(currentUrl1.indexOf(contextPath + "/")===0 && contextPath !== "/") {
        currentUrl1 = currentUrl1.replace(contextPath, "");
    }
    stompClient.send("/app"+currentUrl1+"/addComment", {}, JSON.stringify({'text': $("#comment-textarea").val()}));
    document.getElementById("comment-textarea").value = "";
}

function showComment(comment) {
    var ctxPath = (contextPath!=="/")?contextPath:"";
    $("div.comments").prepend(
        '<div class="comment-author-time">' +
        '<a href="' + ctxPath +'/users/' + comment.userAuthor.id + '">' +
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