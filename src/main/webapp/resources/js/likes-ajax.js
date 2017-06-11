$(document).ready(function() {
        $(".like-form").submit(function(e){
            e.preventDefault();
            var token = $("meta[name='_csrf']").attr("content");
            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('X-CSRF-TOKEN', token);
                }
            });
            var url = $(this).attr('action');
            var numberOfSlashes;
            if(url.indexOf(contextPath+"/") === 0 && contextPath !== "/" && contextPath !== "") {
                numberOfSlashes = 3;
            }  else {
                numberOfSlashes = 2;
            }
            console.log("NUMBER_OF_SLASHES: "+numberOfSlashes);
            var postId = url.split('/')[numberOfSlashes];
            console.log(postId);
            $.ajax({
                url: url,
                type: "POST",
                success: function(data) {
                    if(data != 0){
                        $("#post-"+postId+"-likes").html(data);
                    } else $("#post-"+postId+"-likes").html("");
                }
            });
        });
        });