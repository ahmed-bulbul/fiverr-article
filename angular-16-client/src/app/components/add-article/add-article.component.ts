import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/environments/environments';
import { LocalstorageService } from 'src/app/helper/localstorage.service';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent {

  public baseUrl = environment.baseUrl;

  constructor(private route: ActivatedRoute,
    private articleService: ArticleService,private router: Router,
    private localstorageService: LocalstorageService) { }

  article = {
    title: '',
    body: '',
    likes: 0,
    dislikes: 0,
    disabled: false,
    fileExtension: '',
    image: '',

    // Add other fields here
  };

  submitted = false;

  saveArticle() {
    
    let apiURL = this.baseUrl + "/api/article";
    let formData: any = {};
    formData = this.article;
    this.articleService.sendPostRequest(apiURL, formData).subscribe({
      next: (response: any) => {
        this.submitted = true;
        this.router.navigate(['/articles']);
      },
      error: (error: any) => {
        console.log(error);
      }
    });

    // Handle the form submission logic here

    // console.log(this.article);
    // this.submitted = true;
    // this.articleService.createArticle(this.article).subscribe((res:any) => {
    //   // Handle the response here
    // });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.convertImageToBase64(file);
    }
  }

  convertImageToBase64(file: File): void {
    const reader = new FileReader();

    reader.onload = () => {
      // Set the base64 string to the article.image property
      this.article.image = reader.result as string;

      // Extract the file extension
      const extension = file.name.split('.').pop();
      this.article.fileExtension = extension ? extension.toLowerCase() : '';
    };

    reader.readAsDataURL(file);
  }


  // newArticle() {
  //   // Reset the form for a new article
  //   this.article = {
  //     title: '',
  //     description: '',
  //     image: '',
  //     // Reset other fields here
  //   };
  //   this.submitted = false;
  // }

}
