import { Injectable } from '@angular/core';
import { MyManufacturer } from '../../my-manufacturer'
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { LoggedUtils } from '../../utils/logged.utils'

@Injectable()
export class ManufacturerService {

  constructor(private http: Http) { }

  private addNewManufacturerUrl = '/api/manufacturers/';

  addNewManufacturer(manufacturer): Promise<MyManufacturer> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    manufacturer.userId = LoggedUtils.getId();
    return this.http.post(this.addNewManufacturerUrl, manufacturer, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyManufacturer[])
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
  
}
