const WS_SENDING_INTERVAL = 100;

const promise = navigator.mediaDevices.getUserMedia({ audio: true });

const ws = new WebSocket(`ws://${location.host}${location.pathname}voice`);

ws.onmessage = event => {
    const voices = document.getElementById('voices');
    voices.src = event.data;
};

ws.onopen = () => {

};

ws.onclose = () => {

};

promise.then(stream => {
    const recorder = new MediaRecorder(stream);

    recorder.ondataavailable = event => {
        ws.send(event.data);
    };

    recorder.start(WS_SENDING_INTERVAL);
});

const startRecord = async () => {
    const stream = await promise;
    const recorder = new MediaRecorder(stream);
    recorder.ondataavailable = event => {
        console.log(event.data);
    };

    recorder.start(WS_SENDING_INTERVAL);
};
