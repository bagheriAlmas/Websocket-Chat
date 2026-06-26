function register() {

  fetch(API + "/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      email: email.value,
      password: password.value
    })
  }).then(() => {
    alert("Registered!");
    window.location = "login.html";
  });
}
