document.addEventListener("DOMContentLoaded", function() {
    var messageElement = document.getElementById("message");
    if (messageElement) {
        var message = messageElement.textContent.trim();
        if (message) {
            alert(message);
        }
    }
});