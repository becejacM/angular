import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { LoggedUtils } from '../../utils/logged.utils'
import { MyConfiguration } from '../../my-configuration'


@Injectable()
export class ConfigurationService {

  constructor(private http: Http) { }

  private addNewConfigurationUrl = '/api/configurations/';
  private getMyConfigurationsUrl = '/api/configurations/get_my_configurations/';
  private deleteConfigurationUrl = '/api/configurations/';
  private getAllConfigutaionsUrl = '/api/configurations/all';
  private checkOutUrl = '/api/configurations/check-out';
  private deliverUrl = '/api/configurations/deliver';
  private payUrl = '/api/configurations/pay';
  

  addNewConfiguration(configuration): Promise<MyConfiguration> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    configuration.userId = LoggedUtils.getId();
    return this.http.post(this.addNewConfigurationUrl, configuration, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration[])
      .catch(this.handleError);
  }

  deleteConfiguration(confId): Promise<MyConfiguration> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let userId = LoggedUtils.getId();    
    return this.http.delete(this.deleteConfigurationUrl + "/" + confId + "/" + userId, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration)
      .catch(this.handleError);
  }

  adminDeleteConfiguration(confId, idUser): Promise<MyConfiguration> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.delete(this.deleteConfigurationUrl + "/" + confId + "/" + idUser, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration)
      .catch(this.handleError);
  }

  updateConfiguration(configuration): Promise<MyConfiguration> {
    var headers = new Headers();
    configuration.userId = LoggedUtils.getId();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.addNewConfigurationUrl, configuration, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration)
      .catch(this.handleError);
  }

  getMyConfigurations(page): Promise<MyConfiguration[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let userId = LoggedUtils.getId();    
    let pagingUrl = '&page=' + page + '&size=3';    
    return this.http.get(this.getMyConfigurationsUrl +userId + "?" + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration[])
      .catch(this.handleError);
  }

  getAllConfigurations(page): Promise<MyConfiguration[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let userId = LoggedUtils.getId();    
    let pagingUrl = '&page=' + page + '&size=3';    
    return this.http.get(this.getAllConfigutaionsUrl+"?" + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyConfiguration[])
      .catch(this.handleError);
  }

  checkOut(configuration){
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.checkOutUrl,configuration, { headers: headers })
    .toPromise()
    .then(response => response.json() as MyConfiguration)
    .catch(this.handleError);
  }

  deliver(configuration){
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.deliverUrl, configuration, { headers: headers })
    .toPromise()
    .then(response => response.json() as MyConfiguration)
    .catch(this.handleError);
  }

  pay(configuration){
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.payUrl,configuration, { headers: headers })
    .toPromise()
    .then(response => response.json() as MyConfiguration)
    .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
