import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { LoggedUtils} from "../../utils/logged.utils"

@Injectable()
export class UserService {

  constructor(private http: Http) { }

  register(iName, iEmail, iPass, iRPass) {
    var param = {
      username: iName,
      email: iEmail,
      password: iPass,
      repeatedPassword: iRPass
    }
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post("/api/users/register", param, { headers: headers })
      .map(res => res.json());
  }

  changePassword(changedPassData){
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let changedPass = {userId: LoggedUtils.getId(), oldPassword: changedPassData[0], newPassword: changedPassData[1], repeatedNewPassword: changedPassData[2]};
    return this.http.put("/api/users/password", changedPass, { headers: headers });
  }

  updateProfile(updateUserData) {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let updateUser = {id: LoggedUtils.getId(),email: updateUserData[1], username: updateUserData[0]};
    
    return this.http.put("/api/users/update", updateUser, { headers: headers })
      .map(res => res.json());
  }

}
