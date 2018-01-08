
export class LoggedUtils {
  static getId() {
    if (this.isEmpty())
      return null;
    return JSON.parse(localStorage.getItem("loggedUser")).id;
  }

  static getToken()
  {
    if(this.isEmpty())
      return null;
    return JSON.parse(localStorage.getItem("loggedUser")).token;
  }

  static hasRole(role: string) {
    if (this.isEmpty())
      return null;
    let roles = JSON.parse(localStorage.getItem("loggedUser")).role;
    return roles.indexOf(role) != -1;
  }

  static getRole() {
    if (this.isEmpty())
      return null;
    return JSON.parse(localStorage.getItem("loggedUser")).role;
  }

  static getEmail() {
    if (this.isEmpty())
      return null;
    return JSON.parse(localStorage.getItem("loggedUser")).email;
  }

  static setEmail(emailSet: string) {
    if (this.isEmpty())
      return null;
    var user = JSON.parse(localStorage.getItem("loggedUser"));
    localStorage.clear();
    user.email = emailSet;
    localStorage.setItem(("loggedUser"),JSON.stringify(user));
  }

  static clearLocalStorage() {
    localStorage.clear();
  }

  static isEmpty() {
    return localStorage.getItem("loggedUser") === null;
  }

  static isEnabled() {
    if (JSON.parse(localStorage.getItem("loggedUser")) != null)
      return JSON.parse(localStorage.getItem("loggedUser")).enabled == true;
    return false;
  }

  static isLogged() {

  }

  static getUsername()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).username;
  }

  static getUser()
  {
    return JSON.parse(localStorage.getItem("loggedUser"));
  }

  static getAddress()
  {
    return JSON.parse(localStorage.getItem("loggedUser")).address;
  }

  static getLoggedUser(){
    return localStorage.getItem("loggedUser");
    
  }
}
