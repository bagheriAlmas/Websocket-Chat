const API = "http://localhost:8080";

function getToken() {
  return localStorage.getItem("token");
}

function authHeaders() {
  return {
    "Authorization": "Bearer " + getToken(),
    "Content-Type": "application/json"
  };
}
