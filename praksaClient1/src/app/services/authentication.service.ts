import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService
{
  constructor(private http: Http) {}

  authenticateUser(email: string, pass: string)
  {
    let authenticationRequest = {email: email, password: pass};
    let param = JSON.stringify(authenticationRequest);
    let headers = new Headers();

    headers.append('Content-Type', 'application/json');
    return this.http.post("http://localhost:8080/auth", param, { headers : headers })
      .map(res => res.json());
  }


}
