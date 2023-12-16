import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalstorageService } from 'src/app/helper/localstorage.service';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder,private articleService: ArticleService,private router: Router,private localstorageService:LocalstorageService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log('Username:', this.loginForm.value.username);
    console.log('Password:', this.loginForm.value.password);
    // Add your authentication logic, API calls, etc.

    

    this.articleService.login(this.loginForm.value.username, this.loginForm.value.password);
  }
}
