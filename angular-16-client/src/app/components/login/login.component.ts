import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {

    alert('Thanks!');
    // Handle login logic here
  
    console.log('Username:', this.loginForm.value.username);
    console.log('Password:', this.loginForm.value.password);
    // Add your authentication logic, API calls, etc.
  }
}
