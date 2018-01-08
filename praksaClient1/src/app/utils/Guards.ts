import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { LoggedUtils } from "./logged.utils";

@Injectable()
export class Guard implements CanActivate {

  constructor() {
  }

  canActivate()
  {
    if(!LoggedUtils.isEnabled())
      alert("Can't access that component");
    return LoggedUtils.isEnabled();
  }
}
