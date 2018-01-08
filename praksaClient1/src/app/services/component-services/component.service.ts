import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { MyComponent } from '../../my-component';
import { MyType } from '../../my-type';
import { MyManufacturer } from '../../my-manufacturer';
import { LoggedUtils } from '../../utils/logged.utils'
import { MyComment } from '../../my-comment';

@Injectable()
export class ComponentService {

  constructor(private http: Http) {

  }


  private searchedComponentsUrl = '/api/components/search';
  private allComponentsUrl = '/api/components/all';
  private allComponents = '/api/components/allComponents';
  private alltypesUrl = '/api/types/all';
  private getRequredTypes = '/api/types/get-required-types'
  private allManufacturersUrl = "/api/manufacturers/all";
  private addcomponentUrl = "api/components/";
  private deletecomponentUrl = "api/components";


  private getAllCommentsUrl = "api/comments/get-all-by-component/";
  private addCommentUrl = "api/comments/";
  private deleteCommentUrl = "api/comments";

  private addTypeUrl = "api/types/"


  addNew(component): Promise<MyComponent> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.post(this.addcomponentUrl, component, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent)
      .catch(this.handleError);
  }

  update(component): Promise<MyComponent> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.addcomponentUrl, component, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent)
      .catch(this.handleError);
  }
  updateQuantity(component): Promise<MyComponent> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.put(this.addcomponentUrl, component, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent)
      .catch(this.handleError);
  }
  delete(id): Promise<MyComponent> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.delete(this.deletecomponentUrl + "?id=" + id, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent)
      .catch(this.handleError);
  }
  getAllComponents(page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.allComponentsUrl + '?page=' + page + '&size=10', { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }

  getComponents(): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.allComponents, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }

  getAllTypes(): Promise<MyType[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.alltypesUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyType[])
      .catch(this.handleError);
  }

  getAllManufacturers(): Promise<MyManufacturer[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.allManufacturersUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyManufacturer[])
      .catch(this.handleError);
  }

  searchAll(price, exist, type, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?price=" + price + "&exist=" + exist + "&type=" + type + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }

  searchByPriceAndType(price, type, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?price=" + price + "&type=" + type + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }

  searchByPriceAndExist(price, exist, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?price=" + price + "&exist=" + exist + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }

  searchByExistAndType(exist, type, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?exist=" + exist + "&type=" + type + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }
  searchByPrice(price, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?price=" + price + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }
  searchByExist(exist, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?exist=" + exist + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }
  searchByType(type, page): Promise<MyComponent[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    let pagingUrl = '&page=' + page + '&size=10';
    return this.http.get(this.searchedComponentsUrl + "?type=" + type + pagingUrl, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComponent[])
      .catch(this.handleError);
  }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
  getRequiredComponents(): Promise<MyType[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.getRequredTypes, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyType[])
      .catch(this.handleError);
  }
  addComment(comment): Promise<MyComment> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    comment.userId = LoggedUtils.getId();
    return this.http.post(this.addCommentUrl, comment, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComment)
      .catch(this.handleError);
  }

  getAllCommentsById(id): Promise<MyComment[]> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.get(this.getAllCommentsUrl + id, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComment[])
      .catch(this.handleError);
  }

  deleteComment(id, compId): Promise<MyComment> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.delete(this.deleteCommentUrl + "?id=" + id + "&compId=" + compId, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyComment)
      .catch(this.handleError);
  }

  addType(type): Promise<MyType> {
    var headers = new Headers();
    headers.append("X-Auth-Token", LoggedUtils.getToken());
    return this.http.post(this.addTypeUrl, type, { headers: headers })
      .toPromise()
      .then(response => response.json() as MyType)
      .catch(this.handleError);
  }
}
