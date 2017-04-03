/**
 * Created by Max on 29.03.2017.
 */
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Spieler } from './spieler';
import { SpielerService } from './mock-spieler';
import { LoginTry } from './login.component';
import { isNullOrUndefined } from 'util';
import { Globals } from './globals';

@Injectable()
export class CheckLoginService {
    sp: Spieler[];
    errorMessage: string;
    getSpielerArray(): Spieler[] {
        this.spielerservice.Spielerholen().subscribe(sp => this.sp = sp,
            error =>  this.errorMessage = <any>error);
        self.window.console.log(this.sp);
        return this.sp;
    }
    getRedirect(login: LoginTry): void {
        if (isNullOrUndefined(this.getSpielerArray().find(spieler => spieler.email === login.loginname && spieler.email === login.passwort)
        )) {
            this.router.navigate(['/']);
        } else {
            this.globals.setLoggedIn(this.getSpielerArray().find(spieler => spieler.email === login.loginname && spieler.email === login.passwort));
        this.router.navigate(['/ranking']);
        }
    }
    constructor ( private router: Router,
        private globals: Globals,
        private spielerservice: SpielerService) {
  }
}
