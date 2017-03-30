///<reference path="../../node_modules/rxjs/operator/window.d.ts"/>
/**
 * Created by Max on 28.03.2017.
 */
import { Component } from '@angular/core';
import { CheckLoginService } from './check-login.service';

export class LoginTry {
  loginname: string;
  passwort: string;
}
@Component({
  selector: 'app-login-component',
  template: `
    <div>Email : <input class="input-group-sm" type="text" [(ngModel)]="loginTry.loginname" placeholder="Email-Adresse"></div>
    <div>Passwort : <input class="form-control" type="password" [(ngModel)]="loginTry.passwort" placeholder="Passwort"></div>
    <button class="btn btn-lg btn-block login-btn" (click)="Redirect()">Login</button>    `
})

export class LoginComponent {
  loginTry: LoginTry = {
    loginname: '',
    passwort: ''
  };
  checkLoginService: CheckLoginService;
  Redirect(): void {
    this.checkLoginService.getRedirect(this.loginTry);
  }
  constructor ( checkLoginServiceCons: CheckLoginService) {
    this.checkLoginService = checkLoginServiceCons;
  }
}
