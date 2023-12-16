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
  

  


  constructor(private articleService: ArticleService) {}

  isAdmin: boolean = true;

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    // this.articleService.getArticles(this.currentPage, this.itemsPerPage)
    //   .subscribe(response => {
    //     this.articles = response.articles;
    //     this.totalItems = response.totalItems;
    //   });
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
      dislikes: 0
    },
    {
      id: 2,
      title: 'Article 2',
      author: 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3',
      description: 'Description 2',
      createdAt: new Date(),
      imageUrl: 'https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w1200/2023/10/free-images.jpg',
      likes: 0,
      dislikes: 0
    }
  ]

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadArticles();
  }

  
  totalLikes: number = 0;
  totalDislikes: number = 0;

  likeArticle(article: any) {
    // Increment the likes count for the given article
    article.likes++;
    // Increment the total likes count
    this.totalLikes++;
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
}
