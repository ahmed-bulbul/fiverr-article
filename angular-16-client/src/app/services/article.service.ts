import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Article } from '../models/Article.model';
import { LocalstorageService } from '../helper/localstorage.service';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  public baseUrl = environment.baseUrl;
  private apiUrl = 'http://localhost:8080/api';
  private loginUrl = 'http://localhost:8080/auth/login';

  private testUrl = 'http://localhost:8080/top';

  public username: String = '';
  public password: String = '';

  constructor(private http: HttpClient,private localstorageService: LocalstorageService) {}

  getArticles(page: number, itemsPerPage: number): Observable<{ articles: Article[], totalItems: number }> {
    const url = `${this.apiUrl}/article`;
    return this.http.get<{ articles: Article[], totalItems: number }>(url);
  }

  public sendGetRequest(apiURL:any, formData:any) {

    console.log("@sendGetRequest");
    return this.http.get(apiURL, formData);

  }

  public sendPostRequest(apiURL:any, formData:any) {


    console.log("@sendPostRequest");
    return this.http.post(apiURL, formData);

  }


  //login api. username password passing with basic auth 
  // login(username: string, password: string): Observable<any> {
  //   return this.http.post(`http://localhost:8787/auth/login`,
  //     { headers: { authorization: this.createBasicAuthToken(username, password) } });
  // }

  login(username: string, password: string) {
    return this.http.get(`http://localhost:8787/auth/login`,
      { headers: { authorization: this.createBasicAuthToken(username, password) } }).subscribe((data: any) =>{
        this.username = username;
        this.password = password;
      });
  }
  
  //create article api
  createArticle(article: any): Observable<any> {
      return this.http.post(`http://localhost:8787/api/article`,article);
  }

  createBasicAuthToken(username?: string, password?: string) {
    return 'Basic ' + window.btoa(username + ":" + password);
  }

  
}
