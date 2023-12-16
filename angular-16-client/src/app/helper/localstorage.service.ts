import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class LocalstorageService {
  clearTimeout: any;

  constructor(private router: Router) {}

  // set token in local storage
  setToken(token: string) {
    localStorage.setItem("token", token);
  }

  //get token in local storage
  getToken() {
    return localStorage.getItem("token");
  }


  setUsername(username: string) {
    localStorage.setItem("username", username);
  }

    getUsername() {
        return localStorage.getItem("username");
    }

    setPass(password: string) {
        localStorage.setItem("password", password);
    }

    getPass() {
        return localStorage.getItem("password");
    }


    isLoggedIn(){

        if(this.getUsername()){
            return true;
        }
        else{
            return false;
        }
    }

}