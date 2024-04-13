$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/api/info/ip',
        success: function (response) {
            $('#ipAddress').text(response);
        },
        error: function (xhr, status, error) {
            $('#ipAddress').text("Error: " + error);
        }
    });
});
