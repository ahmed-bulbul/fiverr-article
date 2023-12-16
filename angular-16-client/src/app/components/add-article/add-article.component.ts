import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LocalstorageService } from 'src/app/helper/localstorage.service';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent {

  constructor(private route: ActivatedRoute,private articleService: ArticleService,private localstorageService: LocalstorageService) {}

  article = {
    title: '',
    body: '',
    fileExtension: '',
    image: '',
    
    // Add other fields here
  };

  submitted = false;

  saveArticle() {
    // Handle the form submission logic here
    
    console.log(this.article);
    this.submitted = true;
    this.articleService.createArticle(this.article).subscribe((res:any) => {
      // Handle the response here
    });
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
