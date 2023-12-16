import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalstorageService } from 'src/app/helper/localstorage.service';
import { ArticleService } from 'src/app/services/article.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  username !: any;
  password !: any;
  errorMessage = 'Invalid Credentials';
  successMessage!: any;
  invalidLogin = false;
  loginSuccess = false;
  constructor(private fb: FormBuilder, private articleService: ArticleService,
    private router: Router,
    private localstorageService: LocalstorageService,
    private authenticationService: AuthenticationService) {
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
    this.username = this.loginForm.value.username;
    this.password = this.loginForm.value.password

    //this.articleService.login(this.loginForm.value.username, this.loginForm.value.password);

    this.authenticationService.authenticationService(this.username, this.password).subscribe((result) => {
      this.router.navigate(['/articles']);

      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });
  }

}
