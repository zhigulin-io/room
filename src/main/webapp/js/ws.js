const roomLogin = document.getElementById('room-login');

const roomChat = document.getElementById('room-chat');
const roomChatLog = document.getElementById('room-chat-log');

let ws = null;

const connectToRoom = () => {
    const username = document.getElementById('room-username').value;
    ws = new WebSocket(`ws://${location.host}${location.pathname}text/${username}`);

    ws.onmessage = event => {
        roomChatLog.innerHTML += `<tr><td>${event.data}</td></tr>`;
    };

    ws.onopen = () => {
        roomLogin.hidden = true;
        roomChat.hidden = false;
    };

    ws.onclose = () => {
        roomLogin.hidden = false;
        roomChat.hidden = true;
    };
};

const sendMsg = () => {
    const msg = document.getElementById('room-chat-message');
    if (ws !== null) {
        ws.send(msg.value);
    }
    msg.value = '';
};
