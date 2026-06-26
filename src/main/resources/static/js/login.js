function login() {

  fetch(API + "/api/auth/login", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({
      email: email.value,
      password: password.value
    })
  })
    .then(res => res.json())
    .then(data => {

      localStorage.setItem("token", data.token);

      window.location = "conversations.html";
    });
}
