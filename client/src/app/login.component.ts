/**
 * Created by Max on 28.03.2017.
 */
import {Component, Injectable} from '@angular/core';
import { CheckLoginService } from './check-login.service';

export class LoginTry {
  loginname: string;
  passwort: string;
}
// @Component({
//   selector: 'app-login-component',
//   /*template: `
//       <h3 class="form-signin-heading">Bitte anmelden ...</h3>
//       <input class="form-control" type="text" [(ngModel)]="loginTry.loginname" placeholder="Email-Adresse">
//       <input class="form-control" type="password" [(ngModel)]="loginTry.passwort" placeholder="Passwort">
//       <button class="btn btn-lg btn-block login-btn" (click)="Redirect()">Login</button>
//   `*/
//     // templateUrl: 'login.html'
// })

@Injectable()
export class LoginComponent {
  loginTry: LoginTry = {
    loginname: '',
    passwort: ''
  };
  Redirect(): void {
    this.checkLoginService.doRedirect(this.loginTry);
  }
  constructor ( private checkLoginService: CheckLoginService) {
  }
}
