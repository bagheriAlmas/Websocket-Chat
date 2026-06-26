let stompClient;

window.onload = function () {
  loadHistory();
  connect();
};

function loadHistory() {

  const conversationId = localStorage.getItem("conversationId");

  fetch(API + "/api/chat/" + conversationId, {
    headers: authHeaders()
  })
    .then(res => res.json())
    .then(data => {

      const chat = document.getElementById("chat");
      chat.innerHTML = "";

      data.forEach(m => {

        const div = document.createElement("div");

        if (m.senderEmail === getEmailFromToken()) {
          div.className = "msg-me";
        } else {
          div.className = "msg-other";
        }

        div.innerText = m.content;

        chat.appendChild(div);
      });

      chat.scrollTop = chat.scrollHeight;
    });
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

  if (!receiver) {
    console.error("Receiver is missing!");
    return;
  }

  const message = {
    receiverEmail: receiver,
    content: msg.value,
    conversationId: conversationId
  };

  console.log("receiver from storage:", localStorage.getItem("receiverEmail"));
  console.log(localStorage)

  if (!receiver) {
    alert("No receiver selected! Go back to conversations.");
    return;
  }

  stompClient.send("/app/chat.send", {}, JSON.stringify(message));

  msg.value = "";
}

function addMessage(m) {

  const div = document.createElement("div");

  if (m.senderEmail === getEmailFromToken()) {
    div.className = "msg-me";
  } else {
    div.className = "msg-other";
  }

  div.innerText = m.content;

  document.getElementById("chat").appendChild(div);

  document.getElementById("chat").scrollTop =
    document.getElementById("chat").scrollHeight;
}

function getEmailFromToken() {
  const token = getToken();
  const payload = JSON.parse(atob(token.split(".")[1]));
  return payload.sub;
}
