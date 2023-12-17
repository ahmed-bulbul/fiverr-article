import { Component } from '@angular/core';
import { LocalstorageService } from './helper/localstorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {


  constructor(private localstorageService: LocalstorageService,private route: Router) { }

  title = 'Angular 16 Crud example';
  isLoggedIn = false;


  ngOnInit() {
    this.isLoggedIn = this.localstorageService.isLoggedIn();
    console.log('isLoggedIn', this.isLoggedIn);
  }

  logout() {
    this.localstorageService.logout();
    this.route.navigate(['/login']);
  }
}
