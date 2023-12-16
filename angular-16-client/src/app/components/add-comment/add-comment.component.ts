// add-comment.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/environments/environments';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnInit {
  comment = {
    id: '', // This will be populated from the URL parameter
    text: ''
  };

  public baseUrl = environment.baseUrl;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private router: Router,) {}

  ngOnInit() {
    // Retrieve the 'id' parameter from the URL
    this.route.paramMap.subscribe(params => {
      this.comment.id = params.get('id') || '';
    });
  }

  onSubmit() {
    let apiURL = this.baseUrl + "/api/article/" + this.comment.id + "/comment";
    let formData: any = {};
    formData = this.comment;
    this.articleService.sendPostRequest(apiURL, formData).subscribe({
      next: (response: any) => {

        this.router.navigate(['/articles']);
      },
      error: (error: any) => {
        console.log(error);
      }
    });

  }
}
