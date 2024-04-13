$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/api/info/ip',
        success: function (response) {
            $('#ipAddress').text(response);
            fetchMessages();
            setInterval(fetchMessages, 3000);
        },
        error: function (xhr, status, error) {
            $('#ipAddress').text("Error: " + error);
        }
    });

    function sendMessage() {
        const messageContent = $('#messageInput').val();
        if (messageContent.trim() !== '') {
            $.ajax({
                type: 'POST',
                url: '/api/messages',
                contentType: 'application/json',
                data: JSON.stringify({content: messageContent}),
                success: function () {
                    $('#messageInput').val('');
                    fetchMessages();
                }
            });
        }
    }

    $('#messageInput').keypress(function (event) {
        if (event.which === 13) {
            sendMessage();
            event.preventDefault();
        }
    });

    function fetchMessages() {
        $.ajax({
            type: 'GET',
            url: '/api/messages',
            dataType: 'json',
            success: function (messages) {
                $('#chatArea').empty();
                messages.forEach(function (message) {
                    const isSender = message.sender === $('#ipAddress').text();
                    $('#chatArea').append(`
                        <div class="flex flex-col mx-3 mb-2
                            ${isSender ? 'items-end' : 'items-start'}
                        ">
                            <div class="font-bold">${message.sender}</div>
                            <div class="mb-1 text-xs text-gray-600">${formatDate(message.sendTime)}</div>
                            <div class="px-3 py-1 rounded-md break-words inline-block
                                ${isSender ? 'bg-blue-100' : 'bg-gray-100'}
                            " style="max-width: 75%">
                                <p>${message.content}</p>
                            </div>
                        </div>
                    `);
                });
                $('#chatArea').scrollTop($('#chatArea')[0].scrollHeight);
            }
        });
    }

    function formatDate(timestamp) {
        const date = new Date(timestamp);
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        const hour = ('0' + date.getHours()).slice(-2);
        const minute = ('0' + date.getMinutes()).slice(-2);
        const second = ('0' + date.getSeconds()).slice(-2);
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    }
});
