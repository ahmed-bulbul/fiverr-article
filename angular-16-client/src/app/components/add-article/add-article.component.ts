import { Component } from '@angular/core';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent {

  article = {
    title: '',
    description: '',
    imageExtension: '',
    image: '',
    
    // Add other fields here
  };

  submitted = false;

  saveArticle() {
    // Handle the form submission logic here
    console.log(this.article);
    this.submitted = true;
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
      this.article.imageExtension = extension ? extension.toLowerCase() : '';
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
