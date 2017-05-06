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
            var postId = url.split('/')[2];
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