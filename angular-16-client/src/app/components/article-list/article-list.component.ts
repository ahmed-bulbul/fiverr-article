import { Component } from '@angular/core';
import { Article } from 'src/app/models/Article.model';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent {
  articles: Article[] = [];
  mydata: any = [];
  currentPage = 1;
  itemsPerPage = 5; // Adjust as needed
  totalItems = 0;





  constructor(private articleService: ArticleService) { }

  isAdmin: boolean = true;

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {

    let queryParams = {};
    this.articleService.sendGetRequest(queryParams)
      .subscribe(response => {
        //this.articles = response;
      });
    this.mydata = this.data;
  }

  // make dummy data for testing purposes
  data = [
    {
      id: 1,
      title: 'Article 1',
      author: 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3',
      description: 'Description 1',
      createdAt: new Date(),
      imageUrl: 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w1200/2023/10/free-images.jpg',
      likes: 0,
      dislikes: 0,
      comments: [
        {
          id: 1,
          content: 'Wow thats awsome 1',
          createdAt: new Date()
        },
        {
          id: 2,
          content: 'Awsome post .....  2',
          createdAt: new Date()
        }
      ]
    },
    {
      id: 2,
      title: 'Article 2',
      author: 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3',
      description: 'Description 2',
      createdAt: new Date(),
      imageUrl: 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w1200/2023/10/free-images.jpg',
      likes: 0,
      dislikes: 0,
      comments: [
        {
          id: 1,
          content: 'Very Nice Post',
          createdAt: new Date()
        },
        {
          id: 2,
          content: 'Great post....... 2',
          createdAt: new Date()
        }
      ]
    }
  ]

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadArticles();
  }


  totalLikes: number = 0;
  totalDislikes: number = 0;

  likeArticle(article: any) {
    console.log(article);

    let apiURL = "localhost:8080/api/article/" + article + "/like";
    let formData: any = {};
    this.articleService.sendPostRequest(apiURL, formData).subscribe({
      next: (response: any) => {

      },
      error: (error: any) => {
        console.log(error);
      }
    });

    // Increment the likes count for the given article
    // article.likes++;
    // Increment the total likes count
    //this.totalLikes++;
  }

  dislikeArticle(article: any) {
    // Increment the dislikes count for the given article
    article.dislikes++;
    // Increment the total dislikes count
    this.totalDislikes++;
  }

  disable(id: any): void {
    // Return true if the article has already been liked

    alert("You have already liked this article");

  }
  commentArticle(id: any): void {
    // Return true if the article has already been liked

    alert("You have already liked this article");


  }
}
