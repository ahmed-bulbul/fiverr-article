// add-comment.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnInit {
  comment = {
    id: '', // This will be populated from the URL parameter
    content: ''
  };

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    // Retrieve the 'id' parameter from the URL
    this.route.paramMap.subscribe(params => {
      this.comment.id = params.get('id') || '';
    });
  }

  onSubmit() {
    // Handle the form submission logic here
    console.log('Submitted Comment:', this.comment);
    // Add logic to send the comment to your backend or perform other actions
  }
}
