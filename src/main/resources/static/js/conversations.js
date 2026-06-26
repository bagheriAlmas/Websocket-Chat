window.onload = function () {
  loadChats();
};

function loadChats() {

  fetch(API + "/api/conversations", {headers: authHeaders()})
    .then(res => res.json())
    .then(data => {

      const list = document.getElementById("list");
      list.innerHTML = "";

      data.forEach(c => {

        const div = document.createElement("div");
        div.className = "list-item";

        div.innerText = c.friendEmail + " - " + c.lastMessage;

        div.onclick = function () {


          console.log("clicked conversation:", c);

          const receiver = c.friendEmail;

          if (!receiver) {
            console.error("friendEmail is missing!", c);
            return;
          }

          localStorage.setItem("conversationId", c.conversationId);
          localStorage.setItem("receiverEmail", receiver);

          window.location = "chat.html";
        };

        list.appendChild(div);
      });
    });
}

function getEmailFromToken() {
  const token = localStorage.getItem("token");
  const payload = JSON.parse(atob(token.split(".")[1]));
  return payload.sub;
}
