// basic-auth.interceptor.ts
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalstorageService } from './localstorage.service';


@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

  constructor(private authService: LocalstorageService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("intercepted request ... "+this.authService.getUsername());
    if (this.authService.isLoggedIn() && req.url.indexOf('basicauth') === -1) {
        const authReq = req.clone({
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Basic ${window.btoa(this.authService.getUsername() + ":" + this.authService.getPass())}`
            })
        });
        return next.handle(authReq);
    } else {
        return next.handle(req);
    }
}
}
