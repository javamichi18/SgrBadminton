/**
 * Created by Max on 29.03.2017.
 */
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Spieler } from './spieler';
import { SPIELER } from './mock-spieler';
import { LoginTry } from './login.component';
import { isNullOrUndefined } from 'util';
import { Globals } from './globals';

@Injectable()
export class CheckLoginService {
  getSpieler(): Spieler[] {
    return SPIELER;
  }
  getRedirect(login: LoginTry): void {
    if (isNullOrUndefined(this.getSpieler().find(spieler => spieler.email === login.loginname && spieler.email === login.passwort)
      )) {
      this.router.navigate(['/']);
    } else {
      this.globals.setLoggedIn(this.getSpieler().find(spieler => spieler.email === login.loginname && spieler.email === login.passwort));
      this.router.navigate(['/ranking']);
    }
  }
  constructor ( private router: Router,
    private globals: Globals) {
  }
}
