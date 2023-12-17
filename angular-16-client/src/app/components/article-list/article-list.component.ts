import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/app/environments/environments';
import { Article } from 'src/app/models/Article.model';
import { ArticleService } from 'src/app/services/article.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent {

  public baseUrl = environment.baseUrl;

  articles: Article[] = [];
  mydata: any = [];
  currentPage = 1;
  itemsPerPage = 5; // Adjust as needed
  totalItems = 0;





  constructor(
    private articleService: ArticleService,
    private authenticationService: AuthenticationService,private route: Router) { }

  isAdmin: boolean = true;

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    let apiURL = this.baseUrl + "/api/article";
    let queryParam: any = {};
    this.articleService.sendGetRequest(apiURL, queryParam).subscribe({
      next: (response: any) => {
        this.mydata = response?.model
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  decodeBase64Image(base64String: string): void {
    const imgElement = document.getElementById('previewImage') as HTMLImageElement;
  
    // Set the base64 string as the source of the img element
    imgElement.src = base64String;
    console.log(imgElement.src);
    alert(imgElement.src);
  }


  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadArticles();
  }


  totalLikes: number = 0;
  totalDislikes: number = 0;

  likeArticle(articleId: any) {
    let apiURL = this.baseUrl + "/api/article/" + articleId + "/like";
    let formData: any = {};
    formData.id = articleId;
    this.articleService.sendPutRequest(apiURL, formData).subscribe({
      next: (response: any) => {
        this.loadArticles();
      },
      error: (error: any) => {
        console.log(error);
      }
    });

  }

  dislikeArticle(articleId: any) {
    let apiURL = this.baseUrl + "/api/article/" + articleId + "/dislike";
    let formData: any = {};
    formData.id = articleId;
    this.articleService.sendPutRequest(apiURL, formData).subscribe({
      next: (response: any) => {
        this.loadArticles();
      },
      error: (error: any) => {
        console.log(error);
      }
    });

  }

  disable(articleId: any): void {
    let apiURL = this.baseUrl + "/api/article/" + articleId + "/disable";
    let formData: any = {};
    formData.id = articleId;
    this.articleService.sendPutRequest(apiURL, formData).subscribe({
      next: (response: any) => {
        this.route.navigate(['/articles']);
        this.loadArticles();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
  commentArticle(id: any): void {
    // Return true if the article has already been liked

    alert("You have already liked this article");


  }
}
