$(document).ready(function() {
    $('#submitButton').click(function() {
        var userName = $('#nameInput').val();
        if (userName) {
            $.ajax({
                url: '/api/greeter/hello',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({name: userName}),
                success: function(response) {
                    $('#messageDisplay').text(response.message);
                },
                error: function(xhr, status, error) {
                    $('#messageDisplay').text("Error: " + error);
                }
            });
        } else {
            $('#messageDisplay').text("Please input your name.");
        }
    });
});
