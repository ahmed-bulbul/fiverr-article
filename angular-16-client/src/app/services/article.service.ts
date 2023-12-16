import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../models/Article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl = 'your-api-endpoint'; // Replace with your API endpoint

  constructor(private http: HttpClient) {}

  getArticles(page: number, itemsPerPage: number): Observable<{ articles: Article[], totalItems: number }> {
    const url = `${this.apiUrl}/articles?page=${page}&itemsPerPage=${itemsPerPage}`;
    return this.http.get<{ articles: Article[], totalItems: number }>(url);
  }

  
}
