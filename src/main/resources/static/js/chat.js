let stompClient;

window.onload = function () {
  loadHistory();
  connect();
};

function loadHistory() {

  const conversationId = localStorage.getItem("conversationId");

  if (!conversationId) {
    console.error("conversationId missing");
    return;
  }

  fetch(API + "/api/chat/" + conversationId, {
    headers: authHeaders()
  })
    .then(res => res.json())
    .then(data => {

      const chat = document.getElementById("chat");
      chat.innerHTML = "";

      data.forEach(m => addMessage(m));

      chat.scrollTop = chat.scrollHeight;
    })
    .catch(err => console.error("loadHistory error:", err));
}

function connect() {

  const socket = new SockJS(API + "/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect(
    { Authorization: "Bearer " + getToken() },
    function () {

      stompClient.subscribe("/user/queue/messages", function (msg) {

        const m = JSON.parse(msg.body);
        addMessage(m);
      });
    }
  );
}

function send() {

  const receiver = localStorage.getItem("receiverEmail");
  const conversationId = localStorage.getItem("conversationId");

  const content = msg.value.trim();

  if (!receiver) {
    alert("Receiver is missing!");
    return;
  }

  if (!conversationId) {
    alert("Conversation is missing!");
    return;
  }

  if (!content) return;

  const message = {
    receiverEmail: receiver,
    content: content,
    conversationId: conversationId
  };

  stompClient.send("/app/chat.send", {}, JSON.stringify(message));

  msg.value = "";
}

function addMessage(m) {

  const me = getEmailFromToken();

  const div = document.createElement("div");

  if (m.senderEmail === me) {
    div.className = "msg-me";
  } else {
    div.className = "msg-other";
  }

  div.innerText = m.content;

  const chat = document.getElementById("chat");
  chat.appendChild(div);

  chat.scrollTop = chat.scrollHeight;
}

function getEmailFromToken() {
  const token = getToken();
  const payload = JSON.parse(atob(token.split(".")[1]));
  return payload.sub;
}
