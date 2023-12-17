import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'src/app/environments/environments';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  
  public baseUrl = environment.baseUrl;



  constructor(private fb: FormBuilder,private authenticateService:AuthenticationService,private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      mobile: ['', Validators.required],

    });
  }

  onSubmit() {
    // Handle registration logic here
    let apiURL = this.baseUrl + "/auth/register";
    let formData: any = {};
    formData = this.registerForm.value;
    console.log('Username:', this.registerForm.value.username);

    this.authenticateService.register(apiURL, formData).subscribe({
      next: (response: any) => {
        alert("User Registered Successfully");
        this.router.navigate(['/login']);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
}
}